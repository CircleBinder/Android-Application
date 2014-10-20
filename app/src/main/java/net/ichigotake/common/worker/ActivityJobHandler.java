package net.ichigotake.common.worker;

import android.app.Activity;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Thanks for the answer!
 *
 * http://stackoverflow.com/questions/8040280/how-to-handle-handler-messages-when-activity-fragment-is-paused
 */
class ActivityJobHandler extends AbstractJobHandler {

    private WeakReference<ActionBarActivity> activity;

    public void setActivity(ActionBarActivity activity) {
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
            Message newMessage = obtainMessage();
            newMessage.obj = message.obj;
            sendMessage(newMessage);
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
