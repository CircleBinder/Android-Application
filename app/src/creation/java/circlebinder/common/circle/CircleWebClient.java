package circlebinder.common.circle;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.ichigotake.common.net.NetworkState;

/**
 * notice:
 *  {@link WebView} と共依存ぽくなってしまっているのはよろしくないと思いつつ、
 *  キャッシュを利用するか否かの設定は、HTTPリクエスト直前にやった方が柔軟性が高いため、{@link WebView} 依存がある
 */
public final class CircleWebClient extends WebViewClient {

    private final WebView webView;

    public CircleWebClient(WebView webView) {
        this.webView = webView;
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (NetworkState.isConnected(webView.getContext())) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        return false;
    }

}
