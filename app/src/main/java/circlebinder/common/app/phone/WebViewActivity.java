package circlebinder.common.app.phone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import net.ichigotake.common.app.ActivityNavigation;
import net.ichigotake.common.content.OnAfterLoadingListener;
import net.ichigotake.common.content.OnBeforeLoadingListener;
import net.ichigotake.common.os.BundleMerger;
import net.ichigotake.common.view.MenuPresenter;
import net.ichigotake.common.view.ReloadActionProvider;

import circlebinder.common.app.BaseActivity;
import circlebinder.R;
import circlebinder.common.web.WebView;
import circlebinder.common.web.WebViewClient;
import progress.menu.item.ProgressMenuItemHelper;

public final class WebViewActivity extends BaseActivity {

    private final static String KEY_URL = "url";

    public static Intent createIntent(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(KEY_URL, url);
        return intent;
    }

    private String url;
    private WebView webView;
    private ProgressMenuItemHelper progressMenuItemHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_web_view);
        url = BundleMerger.merge(getIntent(), savedInstanceState).getString(KEY_URL);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView = (WebView)findViewById(R.id.common_activity_web_view);
        WebViewClient webViewClient = new WebViewClient(webView);
        webViewClient.setOnBeforeLoadingListener(new OnBeforeLoadingListener() {
            @Override
            public void onBeforeLoading(String url) {
                if (progressMenuItemHelper != null) {
                    progressMenuItemHelper.startProgress();
                }
            }
        });
        webViewClient.setOnAfterLoadingListener(new OnAfterLoadingListener() {
            @Override
            public void onAfterLoading() {
                if (progressMenuItemHelper != null) {
                    progressMenuItemHelper.stopProgress();
                }
            }
        });
        webView.setWebViewClient(webViewClient);
        webView.loadUrl(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuPresenter presenter = new MenuPresenter(menu, getMenuInflater());
        MenuItem progressItem = presenter
                .inflate(R.menu.app_web_view_progress_bar, R.id.app_web_view_progress_bar);
        progressItem.setVisible(false);
        progressMenuItemHelper = new ProgressMenuItemHelper(progressItem);
        MenuItem reloadItem = presenter.inflate(R.menu.reload, R.id.menu_reload);
        presenter.setActionProvider(reloadItem, new ReloadActionProvider(this, webView));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return ActivityNavigation.back(this, item)
                || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_URL, url);
    }
}
