package circlebinder.creation.app.phone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import net.ichigotake.common.app.ActivityNavigation;
import net.ichigotake.common.os.BundleMerger;
import net.ichigotake.common.worker.ActivityJobWorker;

import circlebinder.common.app.FragmentTripper;
import circlebinder.creation.app.BaseActivity;
import circlebinder.R;
import circlebinder.creation.web.WebViewFragment;

public final class WebViewActivity extends BaseActivity {

    private final static String KEY_URL = "url";

    public static Intent createIntent(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(KEY_URL, url);
        return intent;
    }

    private final ActivityJobWorker worker = new ActivityJobWorker();
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        worker.setActivity(this);
        url = BundleMerger.merge(getIntent(), savedInstanceState).getString(KEY_URL);

        getActionBar().setTitle(R.string.app_event_name);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        worker.enqueueActivityJob(value -> new FragmentTripper(getFragmentManager(), WebViewFragment.factory(url))
                .setAddBackStack(false)
                .setLayoutId(R.id.activity_web_view_container)
                .trip());
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
