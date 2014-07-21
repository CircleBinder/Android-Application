package circlebinder.common.circle;

import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;

final class CircleWebOnKeyListener implements View.OnKeyListener {

    private final WebView webView;

    CircleWebOnKeyListener(WebView webView) {
        this.webView = webView;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return false;
    }
}
