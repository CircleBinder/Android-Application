package circlebinder.common.search;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import net.ichigotake.common.app.ActivityTripper;

import circlebinder.common.app.phone.CircleDetailActivity;
import circlebinder.common.circle.CircleAdapter;
import circlebinder.common.event.BlockBuilder;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

final class CircleSearchPresenter {

    private final Context context;
    private final CircleAdapter adapter;
    private final CircleSearchOptionBuilder searchOptionBuilder;

    CircleSearchPresenter(Context context) {
        this.context = context;
        this.adapter = new CircleAdapter(context);
        this.searchOptionBuilder = new CircleSearchOptionBuilder()
                .setBlock(new BlockBuilder().setName("全").setId(-1).build());
    }

    void attachedView(StickyListHeadersListView view) {
        view.setAdapter(adapter);
    }

    void itemClicked(int position) {
        new ActivityTripper(
                context,
                CircleDetailActivity.createIntent(context, searchOptionBuilder.build(), position)
        ).trip();
    }

    void setFilter(CircleSearchOption searchOption) {
        searchOptionBuilder.set(searchOption);
        adapter.setFilterQueryProvider(new CircleQueryProvider(searchOption));
        adapter.getFilter().filter("");
    }

    void reload() {
        adapter.reload();
    }

    Parcelable onSaveInstanceState(Parcelable superState) {
        SavedState state = new SavedState(superState);
        state.searchOption = searchOptionBuilder.build();
        return state;
    }

    Parcelable onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof CircleSearchPresenter.SavedState)) {
            return null;
        }
        SavedState savedState = (SavedState)state;
        if (savedState.searchOption != null) {
            searchOptionBuilder.set(savedState.searchOption);
        }
        return savedState;
    }

    static class SavedState extends View.BaseSavedState implements Parcelable {

        private CircleSearchOption searchOption;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.searchOption, 0);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public SavedState(Parcel in) {
            super(in);
            this.searchOption = in.readParcelable(CircleSearchOption.class.getClassLoader());
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

}
