package circlebinder.common.web;

import android.webkit.WebSettings;
import android.webkit.WebView;

import net.ichigotake.common.content.OnAfterLoadingListener;
import net.ichigotake.common.content.OnBeforeLoadingListener;
import net.ichigotake.common.content.OnUrlLoadListener;
import net.ichigotake.common.net.NetworkState;

/**
 * notice:
 *  {@link WebView} と共依存ぽくなってしまっているのはよろしくないと思いつつ、
 *  キャッシュを利用するか否かの設定は、HTTPリクエスト直前にやった方が柔軟性が高いため、{@link WebView} 依存がある
 */
public final class WebViewClient extends android.webkit.WebViewClient {

    private final WebView webView;
    private OnUrlLoadListener onUrlLoadListener;
    private OnAfterLoadingListener onAfterLoadingListener;
    private OnBeforeLoadingListener onBeforeLoadingListener;

    public WebViewClient(WebView webView) {
        this.webView = webView;
    }

    public void setOnAfterLoadingListener(OnAfterLoadingListener listener) {
        this.onAfterLoadingListener = listener;
    }

    public void setOnBeforeLoadingListener(OnBeforeLoadingListener listener) {
        this.onBeforeLoadingListener = listener;
    }

    public void setOnUrlLoadListener(OnUrlLoadListener listener) {
        this.onUrlLoadListener = listener;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (NetworkState.isConnected(webView.getContext())) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        if (onUrlLoadListener != null) {
            onUrlLoadListener.onLoadUrl(url);
        }
        return false;
    }

    @Override
    public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
        if (onBeforeLoadingListener != null) {
            onBeforeLoadingListener.onBeforeLoading();
        }
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (onAfterLoadingListener != null) {
            onAfterLoadingListener.onAfterLoading();
        }
        super.onPageFinished(view, url);
    }

}
