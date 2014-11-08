package circlebinder.reitaisai.app.phone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import circlebinder.common.Legacy;
import circlebinder.common.app.BaseActivity;
import circlebinder.R;
import circlebinder.common.app.BroadcastEvent;
import circlebinder.common.card.HomeCardListView;
import circlebinder.reitaisai.home.EventHomepageCard;
import circlebinder.reitaisai.home.EventLocationCard;
import circlebinder.reitaisai.home.EventOfficialTwitterCard;
import circlebinder.reitaisai.home.EventTwitterHashTagCard;

/**
 * 通常起動時のファーストビュー
 */
public final class HomeActivity extends BaseActivity implements Legacy {

    public static Intent createIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    private BroadcastReceiver broadcastReceiver;
    private DrawerLayout drawerLayout;
    private View drawerView;
    private ActionBarDrawerToggle drawerToggle;
    private HomeCardListView homeCardListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reitaisai_activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.reitaisai_activity_home_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.reitaisai_activity_home_container);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerView = findViewById(R.id.reitaisai_activity_home_system_menu);

        homeCardListView = (HomeCardListView) findViewById(R.id.reitaisai_activity_home_content);
        homeCardListView.addItem(new EventHomepageCard(this));
        homeCardListView.addItem(new EventLocationCard(this));
        homeCardListView.addItem(new EventOfficialTwitterCard(this));
        homeCardListView.addItem(new EventTwitterHashTagCard(this));
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                homeCardListView.reload();
            }
        };
        registerReceiver(broadcastReceiver, BroadcastEvent.createIntentFilter());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item)
                || super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(drawerView)) {
            drawerLayout.closeDrawer(drawerView);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
        super.onDestroy();
    }

}
