package circlebinder.common.app.phone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import net.ichigotake.common.app.ActivityNavigation;
import net.ichigotake.common.content.ContentReloader;
import net.ichigotake.common.os.BundleMerger;
import net.ichigotake.common.worker.ActivityJobWorker;

import circlebinder.R;
import circlebinder.common.app.BaseActivity;
import circlebinder.common.app.BroadcastEvent;
import circlebinder.common.app.FragmentTripper;
import circlebinder.common.checklist.ChecklistColor;
import circlebinder.common.search.CircleSearchFragment;
import circlebinder.common.search.CircleSearchOption;
import circlebinder.common.search.CircleSearchOptionBuilder;

public final class ChecklistActivity extends BaseActivity {

    private static final String KEY_CHECKLIST_COLOR = "checklist_color";

    public static Intent createIntent(Context context, ChecklistColor checklistColor) {
        Intent intent = new Intent(context, ChecklistActivity.class);
        Bundle map = new Bundle();
        map.putSerializable(KEY_CHECKLIST_COLOR, checklistColor);
        intent.putExtras(map);
        return intent;
    }

    private ActivityJobWorker worker = new ActivityJobWorker();
    private ChecklistColor checklistColor;
    private BroadcastReceiver broadcastReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_acticity_checklist);
        worker.setActivity(this);
        checklistColor = (ChecklistColor) BundleMerger.merge(getIntent(), savedInstanceState)
                .getSerializable(KEY_CHECKLIST_COLOR);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle(checklistColor.getName());
        CircleSearchOption searchOption = new CircleSearchOptionBuilder()
                .setChecklist(checklistColor).build();

        FragmentTripper.firstTrip(getFragmentManager(), CircleSearchFragment.factory(searchOption))
                .setLayoutId(R.id.common_activity_checklist_container)
                .trip();

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ContentReloader reloader = (ContentReloader) getFragmentManager()
                        .findFragmentById(R.id.common_activity_checklist_container);
                if (reloader != null) {
                    reloader.reload();
                }
            }
        };
        registerReceiver(broadcastReceiver, BroadcastEvent.createIntentFilter());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return ActivityNavigation.back(this, menuItem)
                || super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_CHECKLIST_COLOR, checklistColor);
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
    public void onDestroy() {
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
        super.onDestroy();
    }

}
