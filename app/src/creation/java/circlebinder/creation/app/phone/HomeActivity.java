package circlebinder.creation.app.phone;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.dmitriy.tarasov.android.intents.IntentUtils;

import net.ichigotake.common.content.ContentReloader;
import net.ichigotake.common.content.RawResources;

import java.io.IOException;

import circlebinder.common.app.ActivityTripActionProvider;

import circlebinder.common.Legacy;
import circlebinder.common.app.BaseActivity;
import circlebinder.R;
import circlebinder.common.app.BroadcastEvent;
import circlebinder.common.app.phone.AboutActivity;
import circlebinder.common.app.phone.ContactActivity;
import circlebinder.common.app.phone.EnjoyCreationActivity;

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
        setContentView(R.layout.creation_activity_home);
        getActionBar().setDisplayShowTitleEnabled(false);
        orientationConfig(getResources().getConfiguration());

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ContentReloader reloader = (ContentReloader) getFragmentManager()
                        .findFragmentById(R.id.creation_activity_home_fragment_content);
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
            findViewById(R.id.creation_activity_home_header_event_name).setVisibility(View.GONE);
        } else {
            actionBar.setDisplayShowTitleEnabled(false);
            findViewById(R.id.creation_activity_home_header_event_name).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.event_description, menu);
        MenuItem openWebBrowserItem = menu.findItem(R.id.menu_event_description);
        openWebBrowserItem.setActionProvider(
                new ActivityTripActionProvider(
                        this, EnjoyCreationActivity.createIntent(this)
                )
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
        inflater.inflate(R.menu.wish_me_luck, menu);
        MenuItem contactItem = menu.findItem(R.id.menu_wish_me_luck);
        contactItem.setActionProvider(
                new ActivityTripActionProvider(this, ContactActivity.createIntent(this))
        );
        inflater.inflate(R.menu.change_log, menu);
        MenuItem changeLogItem = menu.findItem(R.id.menu_change_log);
        changeLogItem.setActionProvider(
                new ActivityTripActionProvider(this, ChangeLogActivity.createIntent(this))
        );
        inflater.inflate(R.menu.about_application, menu);
        MenuItem aboutForApplicationItem = menu.findItem(R.id.menu_about_application);
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
