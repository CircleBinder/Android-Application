package circlebinder.common.web;

import android.annotation.SuppressLint;
import android.os.Build;
import android.webkit.WebView;

import net.ichigotake.common.content.ContentReloader;

public final class WebViewContainer implements ContentReloader {

    private final WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    public WebViewContainer(WebView webView) {
        this.webView = webView;

        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webView.getSettings().setJavaScriptEnabled(true);
        } else {
            webView.getSettings().setJavaScriptEnabled(false);
        }
        webView.setOnKeyListener(new OnBackKeyListener(webView));
    }

    public void load(String url) {
        webView.loadUrl(url);
    }

    @Override
    public void reload() {
        webView.reload();
    }

    public void onDestroy() {
        webView.clearCache(true);
        webView.stopLoading();
        webView.setWebChromeClient(null);
        webView.setWebViewClient(null);
        webView.destroy();
    }
}
