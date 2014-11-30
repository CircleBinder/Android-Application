package net.ichigotake.common.util;

import android.app.Activity;
import android.view.View;

public class ActivityViewFinder {

    private final Activity activity;

    public ActivityViewFinder(Activity activity) {
        this.activity = activity;
    }

    @SuppressWarnings("unchecked")
    public <V extends View> Optional<V> find(int id) {
        return Optional.fromNullable((V)activity.findViewById(id));
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V findOrNull(int id) {
        return (V)activity.findViewById(id);
    }

}
