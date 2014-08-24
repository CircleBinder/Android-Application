package circlebinder.creation.app.phone;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import net.ichigotake.common.app.ActivityNavigation;
import net.ichigotake.common.worker.ActivityJobWorker;

import circlebinder.common.search.CircleSearchOption;
import circlebinder.creation.app.BaseActivity;
import circlebinder.R;
import circlebinder.common.search.OnCircleSearchOptionListener;

public final class CircleSearchActivity extends BaseActivity implements OnCircleSearchOptionListener {

    public static Intent createIntent(Context context) {
        return new Intent(context, CircleSearchActivity.class);
    }

    private final ActivityJobWorker worker = new ActivityJobWorker();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_search);
        worker.setActivity(this);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle(R.string.common_circle_search);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return ActivityNavigation.back(this, menuItem)
                || super.onOptionsItemSelected(menuItem);
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
    public void setSearchOption(CircleSearchOption searchOption) {
        worker.enqueueActivityJob(value -> {
            Fragment fragment = value.getFragmentManager().findFragmentById(R.id.activity_circle_search_container);
            if (fragment != null && fragment.isResumed() && fragment instanceof OnCircleSearchOptionListener) {
                ((OnCircleSearchOptionListener)fragment).setSearchOption(searchOption);
            }
        });
    }

}
