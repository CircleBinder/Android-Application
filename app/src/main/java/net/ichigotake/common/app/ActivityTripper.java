package net.ichigotake.common.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * アクティビティの遷移をする
 */
public class ActivityTripper implements Tripper {

    private final String LOG_TAG = ActivityTripper.class.getSimpleName();
    private final Context context;
    private final Intent intent;
    private boolean withFinish;

    public ActivityTripper(Context context, ActivityFactory factory) {
        this.context = context;
        this.intent = factory.create(context);
    }

    public ActivityTripper withFinish() {
        withFinish = true;
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return this;
    }

    public ActivityTripper newTask() {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return this;
    }

    @Override
    public void trip() {
        if (context == null) {
            Log.d(LOG_TAG, "context is null.");
            return ;
        }

        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
        if (withFinish && context instanceof Activity) {
            ((Activity)context).finish();
        }
    }
}
