package circlebinder.common.app.phone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import net.ichigotake.common.app.ActivityNavigation;
import net.ichigotake.common.os.BundleMerger;
import net.ichigotake.common.util.Finders;

import circlebinder.R;
import circlebinder.common.app.BaseActivity;
import circlebinder.common.app.BroadcastEvent;
import circlebinder.common.checklist.ChecklistColor;
import circlebinder.common.search.CircleSearchOption;
import circlebinder.common.search.CircleSearchOptionBuilder;
import circlebinder.common.search.CircleSearchView;

public final class ChecklistActivity extends BaseActivity {

    private static final String KEY_CHECKLIST_COLOR = "checklist_color";

    public static Intent createIntent(Context context, ChecklistColor checklistColor) {
        Intent intent = new Intent(context, ChecklistActivity.class);
        Bundle map = new Bundle();
        map.putSerializable(KEY_CHECKLIST_COLOR, checklistColor);
        intent.putExtras(map);
        return intent;
    }

    private ChecklistColor checklistColor;
    private BroadcastReceiver broadcastReceiver;
    private CircleSearchView checklistView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        checklistColor = (ChecklistColor) BundleMerger.merge(getIntent(), savedInstanceState)
                .getSerializable(KEY_CHECKLIST_COLOR);
        setTheme(checklistColor.getStyleResource());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_acticity_checklist);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(checklistColor.getName());
        actionBar.setDisplayHomeAsUpEnabled(true);
        checklistView = Finders.from(this).find(R.id.common_activity_checklist);
        CircleSearchOption searchOption = new CircleSearchOptionBuilder()
                .setChecklist(checklistColor).build();
        checklistView.setFilter(searchOption);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (checklistView != null) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            checklistView.reload();
                        }
                    });
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
    public void onDestroy() {
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
        super.onDestroy();
    }

}
