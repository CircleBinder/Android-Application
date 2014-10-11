package circlebinder.creation.app.phone;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dmitriy.tarasov.android.intents.IntentUtils;

import net.ichigotake.common.content.ContentReloader;
import net.ichigotake.common.content.RawResources;

import java.io.IOException;

import circlebinder.common.app.ActivityTripActionProvider;

import circlebinder.common.Legacy;
import circlebinder.creation.app.BaseActivity;
import circlebinder.R;
import circlebinder.creation.app.BroadcastEvent;

/**
 * 通常起動時のファーストビュー
 */
public final class HomeActivity extends BaseActivity implements Legacy {

    public static Intent createIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getActionBar().setDisplayShowTitleEnabled(false);
        orientationConfig(getResources().getConfiguration());

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ContentReloader reloader = (ContentReloader) getFragmentManager()
                        .findFragmentById(R.id.activity_home_fragment_content);
                reloader.reload();
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
        ActionBar actionBar = getActionBar();
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            actionBar.setDisplayShowTitleEnabled(true);
            findViewById(R.id.activity_home_header_event_name).setVisibility(View.GONE);
        } else {
            actionBar.setDisplayShowTitleEnabled(false);
            findViewById(R.id.activity_home_header_event_name).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        MenuItem openWebBrowserItem = menu.findItem(R.id.menu_home_open_official_site);
        openWebBrowserItem.setActionProvider(
                new ActivityTripActionProvider(
                        this, EnjoyCreationActivity.createIntent(this)
                )
        );
        MenuItem contactItem = menu.findItem(R.id.menu_home_wish_me_luck);
        contactItem.setActionProvider(
                new ActivityTripActionProvider(this, ContactActivity.createIntent(this))
        );
        try {
            getMenuInflater().inflate(R.menu.event_map, menu);
            String eventMapGeoUrl = new RawResources(getResources()).getText(R.raw.event_map_geo_url).get(0);
            MenuItem openMapItem = menu.findItem(R.id.menu_event_map);
            openMapItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
            openMapItem.setActionProvider(
                    new ActivityTripActionProvider(this, IntentUtils.openLink(eventMapGeoUrl)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        MenuItem changeLogItem = menu.findItem(R.id.menu_home_change_log);
        changeLogItem.setActionProvider(
                new ActivityTripActionProvider(this, ChangeLogActivity.createIntent(this))
        );
        MenuItem aboutForApplicationItem = menu.findItem(R.id.menu_home_about);
        aboutForApplicationItem.setActionProvider(
                new ActivityTripActionProvider(this, AboutActivity.createIntent(this))
        );
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
