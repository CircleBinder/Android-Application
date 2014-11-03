package circlebinder.common.web;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;

import net.ichigotake.common.content.ContentReloader;

public class WebView extends android.webkit.WebView implements ContentReloader {

    private final WebViewPresenter presenter;
    private SavedState savedState;

    @SuppressWarnings("unused") // Public API
    public WebView(Context context) {
        super(context);
        presenter = new WebViewPresenter(this);
    }

    @SuppressWarnings("unused") // Public API
    public WebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        presenter = new WebViewPresenter(this);
    }

    @SuppressWarnings("unused") // Public API
    public WebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        presenter = new WebViewPresenter(this);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        this.savedState = (SavedState) state;
        super.onRestoreInstanceState(this.savedState.getSuperState());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        presenter.webViewAttached();
        if (savedState != null) {
            presenter.loadUrl(savedState.url);
        }
    }

    @Override
    public void destroy() {
        presenter.webViewDetached();
        super.destroy();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        SavedState state = new SavedState(parcelable);
        state.url = getUrl();
        return state;
    }

    static class SavedState extends BaseSavedState implements Parcelable {

        private String url;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeString(this.url);
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.url = in.readString();
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
