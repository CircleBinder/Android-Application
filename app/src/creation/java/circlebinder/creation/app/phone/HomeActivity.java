package circlebinder.creation.app.phone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import net.ichigotake.common.util.ActivityViewFinder;
import net.ichigotake.common.util.Finders;
import net.ichigotake.common.util.Optional;

import circlebinder.common.Legacy;
import circlebinder.common.app.BaseActivity;
import circlebinder.R;
import circlebinder.common.app.BroadcastEvent;
import circlebinder.creation.home.HomeCardListView;
import circlebinder.creation.system.NavigationDrawerRenderer;

/**
 * 通常起動時のファーストビュー
 */
public final class HomeActivity extends BaseActivity implements Legacy {

    public static Intent createIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    private Optional<BroadcastReceiver> broadcastReceiver = Optional.empty();
    private HomeCardListView homeCardListView;
    private NavigationDrawerRenderer drawerRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creation_activity_home);
        ActivityViewFinder finder = Finders.from(this);
        Toolbar toolbar = finder.findOrNull(R.id.creation_activity_home_toolbar);
        setSupportActionBar(toolbar);
        drawerRenderer = new NavigationDrawerRenderer(
                this,
                toolbar,
                finder.<DrawerLayout>findOrNull(R.id.creation_activity_home_container),
                finder.findOrNull(R.id.creation_activity_home_system_menu)
        );
        homeCardListView = finder.findOrNull(R.id.creation_activity_home_checklist_list);
        broadcastReceiver = Optional.<BroadcastReceiver>of(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                homeCardListView.reload();
            }
        });
        registerReceiver(broadcastReceiver.get(), BroadcastEvent.createIntentFilter());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerRenderer.onPostCreate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerRenderer.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerRenderer.onOptionsItemSelected(item)
                || super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!drawerRenderer.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void onDestroy() {
        for (BroadcastReceiver registeredReceiver : broadcastReceiver.asSet()) {
            unregisterReceiver(registeredReceiver);
        }
        super.onDestroy();
    }

}
