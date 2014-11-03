package circlebinder.common.app.phone;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.dmitriy.tarasov.android.intents.IntentUtils;

import net.ichigotake.common.app.ActivityNavigation;
import net.ichigotake.common.app.ActivityTripper;
import net.ichigotake.common.content.OnBeforeLoadingListener;
import net.ichigotake.common.os.BundleMerger;
import net.ichigotake.common.view.ActionProvider;
import net.ichigotake.common.view.MenuPresenter;
import net.ichigotake.common.view.ReloadActionProvider;

import circlebinder.common.Legacy;
import circlebinder.common.checklist.ChecklistSelectActionProvider;
import circlebinder.common.circle.CircleDetailHeaderView;
import circlebinder.common.circle.CircleWebView;
import circlebinder.common.event.Circle;
import circlebinder.common.search.CircleSearchOption;
import circlebinder.common.app.BaseActivity;
import circlebinder.R;
import circlebinder.common.search.CircleCursorConverter;
import circlebinder.common.search.CircleLoader;
import circlebinder.common.web.WebViewClient;

public final class CircleDetailActivity extends BaseActivity
        implements Legacy, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String EXTRA_KEY_SEARCH_OPTION = "search_option";
    private static final String EXTRA_KEY_POSITION = "position";

    public static Intent createIntent(Context context, CircleSearchOption searchOption, int position) {
        Intent intent = new Intent(context, CircleDetailActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(EXTRA_KEY_SEARCH_OPTION, searchOption);
        extras.putInt(EXTRA_KEY_POSITION, position);
        intent.putExtras(extras);
        return intent;
    }

    private CircleSearchOption searchOption;
    private int currentPosition;
    private CircleDetailHeaderView actionBarHeaderView;
    private String currentUrl;
    private CircleWebView webView;
    private Circle circle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.common_activity_circle_detail);

        actionBarHeaderView = new CircleDetailHeaderView(this);
        actionBar.setCustomView(actionBarHeaderView);

        Bundle bundle = BundleMerger.merge(getIntent(), savedInstanceState);
        searchOption = bundle.getParcelable(EXTRA_KEY_SEARCH_OPTION);
        currentPosition = bundle.getInt(EXTRA_KEY_POSITION);

        webView = (CircleWebView)findViewById(R.id.common_activity_circle_detail_web_view);
        WebViewClient client = new WebViewClient(webView);
        client.setOnBeforeLoadingListener(new OnBeforeLoadingListener() {
            @Override
            public void onBeforeLoading(String url) {
                currentUrl = url;
                actionBar.invalidateOptionsMenu();
            }
        });
        webView.setWebViewClient(client);
        getLoaderManager().initLoader(0, bundle, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuPresenter presenter = new MenuPresenter(menu, getMenuInflater());
        MenuItem shareItem = presenter.inflate(R.menu.share, R.id.menu_share);
        presenter.setActionProvider(shareItem, new ActionProvider(this, new ActionProvider.OnClickListener() {
            @Override
            public void onClick() {
                new ActivityTripper(
                        getApplicationContext(),
                        IntentUtils.shareText(circle.getName(), currentUrl)
                ).trip();
            }
        }));
        presenter.setShowAsAction(shareItem, MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        MenuItem openBrowserItem = presenter.inflate(R.menu.open_browser, R.id.menu_open_browser);
        presenter.setActionProvider(openBrowserItem, new ActionProvider(this, new ActionProvider.OnClickListener() {
            @Override
            public void onClick() {
                new ActivityTripper(getApplicationContext(), IntentUtils.openLink(currentUrl)).trip();
            }
        }));
        presenter.setShowAsAction(openBrowserItem, MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        MenuItem reloadItem = presenter.inflate(R.menu.reload, R.id.menu_reload);
        presenter.setActionProvider(reloadItem, new ReloadActionProvider(this, webView));
        presenter.setShowAsAction(reloadItem, MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        MenuItem selectorItem = presenter.inflate(R.menu.checklist_selector, R.id.menu_checklist_selector);
        presenter.setActionProvider(selectorItem, new ChecklistSelectActionProvider(this, circle));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return ActivityNavigation.back(this, menuItem)
                || super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_KEY_SEARCH_OPTION, searchOption);
        outState.putInt(EXTRA_KEY_POSITION, currentPosition);
    }

    private void onCirclePageChanged(Circle circle) {
        this.actionBarHeaderView.setCircle(circle);
        invalidateOptionsMenu();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CircleLoader(getApplicationContext(), searchOption);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.moveToPosition(currentPosition);
        circle = new CircleCursorConverter().create(data);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                webView.setCircle(circle);
                postEvent();
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void postEvent() {
        onCirclePageChanged(circle);
    }

}
