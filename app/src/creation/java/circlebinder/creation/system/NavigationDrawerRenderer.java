package circlebinder.creation.system;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import circlebinder.R;

public class NavigationDrawerRenderer {

    private final View drawerView;
    private final DrawerLayout drawerLayout;
    private final ActionBarDrawerToggle drawerToggle;

    public NavigationDrawerRenderer(
            Activity activity,
            Toolbar toolbar,
            DrawerLayout drawerLayout,
            View drawerView) {
        this.drawerView = drawerView;
        this.drawerLayout = drawerLayout;
        this.drawerToggle = new ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(drawerToggle);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item);
    }

    public boolean onBackPressed() {
        boolean handled;
        if (drawerLayout.isDrawerOpen(drawerView)) {
            drawerLayout.closeDrawer(drawerView);
            handled = true;
        } else {
            handled = false;
        }
        return handled;
    }

    public void onPostCreate() {
        drawerToggle.syncState();
    }

}
