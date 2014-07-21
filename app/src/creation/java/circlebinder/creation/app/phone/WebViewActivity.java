package circlebinder.creation.app.phone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import net.ichigotake.common.app.ActivityFactory;
import net.ichigotake.common.app.ActivityNavigation;
import net.ichigotake.common.app.ActivityTripper;
import net.ichigotake.common.os.BundleMerger;

import circlebinder.creation.BaseActivity;
import circlebinder.R;
import circlebinder.creation.web.WebViewFragment;

public final class WebViewActivity extends BaseActivity {

    private final static String KEY_URL = "url";

    public static ActivityTripper tripper(Context context, String url) {
        return new ActivityTripper(context, factory(url));
    }

    public static ActivityFactory factory(final String url) {
        return new ActivityFactory() {
            @Override
            public Intent create(Context context) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra(KEY_URL, url);
                return intent;
            }
        };
    }

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        url = BundleMerger.merge(getIntent(), savedInstanceState).getString(KEY_URL);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        WebViewFragment
                .tripper(getFragmentManager(), url)
                .setAddBackStack(false)
                .setLayoutId(R.id.activity_web_view_container)
                .trip();
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
