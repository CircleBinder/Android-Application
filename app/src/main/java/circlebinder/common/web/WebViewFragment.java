package circlebinder.common.web;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import net.ichigotake.common.app.FragmentFactory;
import net.ichigotake.common.content.OnAfterLoadingListener;
import net.ichigotake.common.content.OnBeforeLoadingListener;
import net.ichigotake.common.os.BundleMerger;
import net.ichigotake.common.view.MenuPresenter;
import net.ichigotake.common.view.ReloadActionProvider;

import circlebinder.common.app.BaseFragment;
import circlebinder.R;
import progress.menu.item.ProgressMenuItemHelper;

public final class WebViewFragment extends BaseFragment {

    public static FragmentFactory<WebViewFragment> factory(final String url) {
        return new FragmentFactory<WebViewFragment>() {
            @Override
            public WebViewFragment create() {
                return WebViewFragment.newInstance(url);
            }
        };
    }

    static WebViewFragment newInstance(String url) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString(KEY_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    private static final String KEY_URL = "url";
    private String url;
    private WebView webView;
    private ProgressMenuItemHelper progressMenuItemHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        url = BundleMerger.merge(getArguments(), savedInstanceState).getString(KEY_URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.common_fragment_web_view, parent, false);
        webView = (WebView)view.findViewById(R.id.common_fragment_web_view);
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
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuPresenter presenter = new MenuPresenter(menu, inflater);
        MenuItem progressItem = presenter
                .inflate(R.menu.app_web_view_progress_bar, R.id.app_web_view_progress_bar);
        progressItem.setVisible(false);
        progressMenuItemHelper = new ProgressMenuItemHelper(progressItem);
        MenuItem reloadItem = presenter.inflate(R.menu.reload, R.id.menu_reload);
        presenter.setActionProvider(reloadItem, new ReloadActionProvider(getActivity(), webView));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        webView.loadUrl(url);
    }

}
