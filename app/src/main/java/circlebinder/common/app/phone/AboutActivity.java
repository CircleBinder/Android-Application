package circlebinder.common.app.phone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import net.ichigotake.common.app.ActivityNavigation;
import net.ichigotake.common.worker.ActivityJobWorker;
import net.ichigotake.common.worker.ActivityJobWorkerClient;

import circlebinder.common.app.BaseActivity;
import circlebinder.R;

public final class AboutActivity extends BaseActivity implements ActivityJobWorkerClient {

    public static Intent createIntent(Context context) {
        return new Intent(context, AboutActivity.class);
    }

    private final ActivityJobWorker worker = new ActivityJobWorker();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        worker.setActivity(this);
        setContentView(R.layout.common_activity_about);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return ActivityNavigation.back(this, item)
                || super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        worker.resume();
    }

    @Override
    public void onPause() {
        worker.pause();
        super.onPause();
    }

    @Override
    public ActivityJobWorker getWorker() {
        return worker;
    }
}
