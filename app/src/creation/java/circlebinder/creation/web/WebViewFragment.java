package circlebinder.creation.web;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import net.ichigotake.common.app.FragmentFactory;
import net.ichigotake.common.os.BundleMerger;

import circlebinder.common.circle.CircleWebContainer;
import circlebinder.creation.app.BaseFragment;
import circlebinder.R;

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
    private CircleWebContainer container;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String restoredUrl = BundleMerger.merge(getArguments(), savedInstanceState).getString(KEY_URL);
        url = (restoredUrl != null) ? restoredUrl : "https://google.co.jp";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_web_view, parent, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        container = new CircleWebContainer(
                (WebView)getView().findViewById(R.id.fragment_web_view)
        );
        container.loadUrl(url);
    }

    @Override
    public void onDestroy() {
        if (container != null) {
            container.onDestroy();
        }
        super.onDestroy();
    }
}
