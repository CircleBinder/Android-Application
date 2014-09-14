package circlebinder.common.web;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import net.ichigotake.common.app.FragmentFactory;
import net.ichigotake.common.os.BundleMerger;
import net.ichigotake.common.view.ReloadActionProvider;

import circlebinder.creation.app.BaseFragment;
import circlebinder.R;
import progress.menu.item.ProgressMenuItemHelper;

public final class WebViewFragment extends BaseFragment {

    public static FragmentFactory<WebViewFragment> factory(final String url) {
        return () -> WebViewFragment.newInstance(url);
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
    private WebViewContainer container;
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
        WebView webView = (WebView)view.findViewById(R.id.fragment_web_view);
        WebViewClient webViewClient = new WebViewClient(webView);
        webViewClient.setOnBeforeLoadingListener((url) -> {
            if (progressMenuItemHelper != null) {
                progressMenuItemHelper.startProgress();
            }
        });
        webViewClient.setOnAfterLoadingListener(() -> {
            if (progressMenuItemHelper != null) {
                progressMenuItemHelper.stopProgress();
            }
        });
        webView.setWebViewClient(webViewClient);
        container = new WebViewContainer(webView);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.app_web_view_progress_bar, menu);
        progressMenuItemHelper = new ProgressMenuItemHelper(menu, R.id.app_web_view_progress_bar);
        inflater.inflate(R.menu.reload, menu);
        menu.findItem(R.id.menu_reload)
                .setActionProvider(new ReloadActionProvider(getActivity(), container));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        container.load(url);
    }

    @Override
    public void onDestroy() {
        if (container != null) {
            container.onDestroy();
        }
        super.onDestroy();
    }
}
