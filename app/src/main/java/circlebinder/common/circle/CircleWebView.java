package circlebinder.common.circle;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;

import circlebinder.common.event.Circle;
import circlebinder.common.web.WebView;
import circlebinder.common.web.WebViewPresenter;
import circlebinder.common.web.WebViewState;

public class CircleWebView extends WebView {

    private final WebViewPresenter presenter;
    private WebViewState state;

    @SuppressWarnings("unused") // Public API
    public CircleWebView(Context context) {
        super(context);
        presenter = new WebViewPresenter(this);
    }

    @SuppressWarnings("unused") // Public API
    public CircleWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        presenter = new WebViewPresenter(this);
    }

    @SuppressWarnings("unused") // Public API
    public CircleWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        presenter = new WebViewPresenter(this);
    }

    public void setCircle(Circle circle) {
        loadUrl(getCircleLink(circle));
    }

    private String getCircleLink(Circle circle) {
        if (circle.getLinks().isEmpty()) {
            return "https://google.co.jp/search?q="
                    + "\"" + circle.getPenName() + "\""
                    + "%20"
                    + "\"" + circle.getName() + "\"";
        }
        return circle.getLinks().get(0).getUri().toString();
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
