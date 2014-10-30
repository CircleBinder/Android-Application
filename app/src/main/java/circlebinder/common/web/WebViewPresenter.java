package circlebinder.common.web;

import android.os.Build;
import android.os.Parcelable;
import android.webkit.WebSettings;
import android.webkit.WebView;

public final class WebViewPresenter {

    private final WebView webView;

    public WebViewPresenter(WebView webView) {
        this.webView = webView;
    }

    public void webViewAttached() {
        WebSettings settings = webView.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            settings.setJavaScriptEnabled(true);
        } else {
            settings.setJavaScriptEnabled(false);
        }
        webView.setOnKeyListener(new OnBackKeyListener(webView));
    }

    public void webViewDetached() {
        webView.clearCache(true);
        webView.stopLoading();
        webView.setWebChromeClient(null);
        webView.setWebViewClient(null);
        webView.destroy();
    }

    public void loadUrl(String url) {
        webView.loadUrl(url);
    }

}
