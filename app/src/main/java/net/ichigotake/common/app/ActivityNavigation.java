package net.ichigotake.common.app;

import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

public final class ActivityNavigation {

    public static ActionBarActivity getSupportActivity(Activity activity) {
        return (ActionBarActivity)activity;
    }

    public static ActionBar getSupportActionBar(Activity activity) {
        return getSupportActivity(activity).getSupportActionBar();
    }

    public static void setDisplayHomeAsUpEnabled(Activity activity) {
        getSupportActivity(activity).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static boolean hasParentActivity(Activity activity) {
        return activity != null && activity.getParentActivityIntent() != null;
    }

    public static boolean back(Activity currentActivity, MenuItem item) {
        if (item.getItemId() != android.R.id.home) {
            return false;
        }

        Intent upIntent = currentActivity.getParentActivityIntent();
        if (upIntent != null) {
            if (currentActivity.shouldUpRecreateTask(upIntent)) {
                TaskStackBuilder.create(currentActivity)
                        .addNextIntentWithParentStack(upIntent)
                        .startActivities();
            } else {
                currentActivity.navigateUpTo(upIntent);
            }
        } else {
            currentActivity.finish();
        }
        return true;
    }

}
