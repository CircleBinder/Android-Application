package circlebinder.common.circle;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.ichigotake.common.content.AfterLoadingListener;
import net.ichigotake.common.content.BeforeLoadingListener;
import net.ichigotake.common.content.OnUrlLoadListener;
import net.ichigotake.common.net.NetworkState;

/**
 * notice:
 *  {@link WebView} と共依存ぽくなってしまっているのはよろしくないと思いつつ、
 *  キャッシュを利用するか否かの設定は、HTTPリクエスト直前にやった方が柔軟性が高いため、{@link WebView} 依存がある
 */
public final class CircleWebClient extends WebViewClient {

    private final WebView webView;
    private OnUrlLoadListener onUrlLoadListener;
    private AfterLoadingListener afterLoadingListener;
    private BeforeLoadingListener beforeLoadingListener;

    public CircleWebClient(WebView webView) {
        this.webView = webView;
    }

    public void setAfterLoadingListener(AfterLoadingListener listener) {
        this.afterLoadingListener = listener;
    }

    public void setBeforeLoadingListener(BeforeLoadingListener listener) {
        this.beforeLoadingListener = listener;
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
        if (beforeLoadingListener != null) {
            beforeLoadingListener.onBeforeLoading();
        }
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (afterLoadingListener != null) {
            afterLoadingListener.onAfterLoading();
        }
        super.onPageFinished(view, url);
    }

}
