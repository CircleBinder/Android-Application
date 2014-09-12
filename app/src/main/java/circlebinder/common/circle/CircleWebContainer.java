package circlebinder.common.circle;

import android.annotation.SuppressLint;
import android.os.Build;
import android.webkit.WebView;

import net.ichigotake.common.content.AfterLoadingListener;
import net.ichigotake.common.content.BeforeLoadingListener;
import net.ichigotake.common.content.ContentReloader;

import circlebinder.common.event.Circle;
import circlebinder.common.event.CircleLink;

public final class CircleWebContainer implements ContentReloader {

    private final WebView webView;
    private AfterLoadingListener afterLoadingListener;
    private BeforeLoadingListener beforeLoadingListener;

    @SuppressLint("SetJavaScriptEnabled")
    public CircleWebContainer(WebView webView) {
        this.webView = webView;

        CircleWebClient client = new CircleWebClient(webView);
        client.setAfterLoadingListener(() -> {
            if (afterLoadingListener != null) {
                afterLoadingListener.onAfterLoading();
            }
        });
        client.setBeforeLoadingListener(() -> {
            if (beforeLoadingListener != null) {
                beforeLoadingListener.onBeforeLoading();
            }
        });
        webView.setWebViewClient(client);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webView.getSettings().setJavaScriptEnabled(true);
        } else {
            webView.getSettings().setJavaScriptEnabled(false);
        }
        webView.setOnKeyListener(new CircleWebOnKeyListener(webView));
    }

    public void setAfterLoadingListener(AfterLoadingListener listener) {
        this.afterLoadingListener = listener;
    }

    public void setBeforeLoadingListener(BeforeLoadingListener listener) {
        this.beforeLoadingListener = listener;
    }

    public void loadUrl(CircleLink link) {
        loadUrl(link.getUri().toString());
    }

    public void loadUrl(String url) {
        webView.loadUrl(url);
    }

    public void load(Circle circle) {
        webView.loadUrl(
                "https://google.co.jp/search?q="
                        + "\"" + circle.getPenName() + "\""
                        + "%20"
                        + "\"" + circle.getName() + "\""
        );
    }

    @Override
    public void reload() {
        webView.reload();
    }

    public String getCurrentUrl() {
        return webView.getUrl();
    }

    public void onDestroy() {
        webView.clearCache(true);
        webView.stopLoading();
        webView.setWebChromeClient(null);
        webView.setWebViewClient(null);
        webView.destroy();
    }
}
