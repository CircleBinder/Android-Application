package circlebinder.common.web;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;

import net.ichigotake.common.content.ContentReloader;

public class WebView extends android.webkit.WebView implements ContentReloader {

    private final WebViewPresenter presenter;
    private WebViewState state;

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
        super.onRestoreInstanceState(state);
        this.state = presenter.onRestoreInstanceState(state);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        presenter.webViewAttached();
        if (state != null) {
            presenter.loadUrl(state.getUrl());
        }
    }

    @Override
    public void destroy() {
        presenter.webViewDetached();
        super.destroy();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        super.onSaveInstanceState();
        return presenter.onSaveInstanceState();
    }

}
