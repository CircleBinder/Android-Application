package circlebinder.creation.app.phone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dmitriy.tarasov.android.intents.IntentUtils;

import net.ichigotake.common.app.ActivityNavigation;
import net.ichigotake.common.content.RawResources;
import net.ichigotake.common.view.MenuPresenter;

import java.io.IOException;

import circlebinder.common.app.ActivityTripActionProvider;

import circlebinder.common.Legacy;
import circlebinder.common.app.BaseActivity;
import circlebinder.R;
import circlebinder.common.app.BroadcastEvent;
import circlebinder.common.app.phone.AboutActivity;
import circlebinder.common.app.phone.ContactActivity;
import circlebinder.creation.checklist.ChecklistListView;

/**
 * 通常起動時のファーストビュー
 */
public final class HomeActivity extends BaseActivity implements Legacy {

    public static Intent createIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    private BroadcastReceiver broadcastReceiver;
    private ChecklistListView checklistListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creation_activity_home);
        orientationConfig(getResources().getConfiguration());

        checklistListView = (ChecklistListView) findViewById(R.id.creation_activity_home_fragment_content);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                checklistListView.reload();
            }
        };
        registerReceiver(broadcastReceiver, BroadcastEvent.createIntentFilter());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        orientationConfig(newConfig);
    }

    private void orientationConfig(Configuration configuration) {
        ActionBar actionBar = ActivityNavigation.getSupportActionBar(this);
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            actionBar.setDisplayShowTitleEnabled(true);
            findViewById(R.id.creation_activity_home_header_event_name).setVisibility(View.GONE);
        } else {
            actionBar.setDisplayShowTitleEnabled(false);
            findViewById(R.id.creation_activity_home_header_event_name).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuPresenter presenter = new MenuPresenter(menu, getMenuInflater());
        MenuItem descriptionItem = presenter.inflate(R.menu.event_description, R.id.menu_event_description);
        presenter.setActionProvider(descriptionItem, new ActivityTripActionProvider(
                        this, EnjoyCreationActivity.createIntent(this)
                ));
        try {
            String eventMapGeoUrl = new RawResources(getResources()).getText(R.raw.event_map_geo_url).get(0);
            MenuItem mapItem = presenter.inflate(R.menu.event_map, R.id.menu_event_map);
            presenter.setActionProvider(
                    mapItem,
                    new ActivityTripActionProvider(this, IntentUtils.openLink(eventMapGeoUrl)));
            presenter.setShowAsAction(mapItem, MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MenuItem feedbackItem = presenter.inflate(R.menu.wish_me_luck, R.id.menu_wish_me_luck);
        presenter.setActionProvider(
                feedbackItem,
                new ActivityTripActionProvider(this, ContactActivity.createIntent(this)));
        MenuItem changeLogItem = presenter.inflate(R.menu.change_log, R.id.menu_change_log);
        presenter.setActionProvider(
                changeLogItem,
                new ActivityTripActionProvider(this, ChangeLogActivity.createIntent(this)));
        MenuItem aboutApplicationItem = presenter.inflate(R.menu.about_application, R.id.menu_about_application);
        presenter.setActionProvider(
                aboutApplicationItem,
                new ActivityTripActionProvider(this, AboutActivity.createIntent(this)));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onDestroy() {
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
        super.onDestroy();
    }

}
