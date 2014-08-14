package net.ichigotake.common.worker;

import android.app.Activity;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Thanks for the answer!
 *
 * http://stackoverflow.com/questions/8040280/how-to-handle-handler-messages-when-activity-fragment-is-paused
 */
class ActivityJobHandler extends AbstractJobHandler {

    private WeakReference<Activity> activity;

    public void setActivity(Activity activity) {
        this.activity = new WeakReference<>(activity);
    }

    @Override
    protected boolean storeMessage(Message message) {
        return true;
    }

    @Override
    protected void processMessage(Message message) {
        boolean isActivityDead = activity == null || activity.isEnqueued();
        if (isActivityDead) {
            pause();
            sendMessage(message);
            return;
        }

        Object job = message.obj;
        if (job instanceof ActivityJob) {
            ((ActivityJob)job).run(activity.get());
        } else {
            Log.i("ActivityJobHandler", "Unknown job: " + job.getClass().getName());
        }
    }
}
