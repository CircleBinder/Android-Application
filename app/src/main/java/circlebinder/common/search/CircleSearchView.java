package circlebinder.common.search;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import circlebinder.R;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public final class CircleSearchView extends FrameLayout {

    private final CircleSearchPresenter presenter;

    public CircleSearchView(Context context) {
        super(context);
        presenter = new CircleSearchPresenter(context);
    }

    public CircleSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        presenter = new CircleSearchPresenter(context);
    }

    public CircleSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        presenter = new CircleSearchPresenter(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("unused") // Public API
    public CircleSearchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        presenter = new CircleSearchPresenter(context);
    }

    @Override
    protected void onFinishInflate() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.common_circle_search, this, true);
        StickyListHeadersListView circlesView = (StickyListHeadersListView)view
                .findViewById(R.id.common_circle_search);
        presenter.attachedView(circlesView);
        circlesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.itemClicked(position);
            }
        });
    }

    public void setFilter(CircleSearchOption searchOption) {
        presenter.setFilter(searchOption);
    }

    public void reload() {
        presenter.reload();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return presenter.onSaveInstanceState(super.onSaveInstanceState());
    }

    @Override
    public void onRestoreInstanceState(Parcelable state){
        super.onRestoreInstanceState(state);
        presenter.onRestoreInstanceState(state);
        presenter.reload();
    }

}
