package net.ichigotake.common.util;

import android.app.Activity;
import android.view.View;

public class Finders {

    public static ActivityViewFinder from(Activity activity) {
        return new ActivityViewFinder(activity);
    }

    public static ViewFinder from(View view) {
        return new ViewFinder(view);
    }

}
