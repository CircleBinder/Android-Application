package net.ichigotake.common.util;

import android.app.Activity;
import android.view.View;

public class ActivityViewFinder {

    private final Activity activity;

    public ActivityViewFinder(Activity activity) {
        this.activity = activity;
    }

    public <V extends View> V find(int id) {
        return (V)activity.findViewById(id);
    }

}
