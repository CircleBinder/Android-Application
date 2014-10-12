package net.ichigotake.common.worker;

import android.app.Activity;
import android.os.Message;

/**
 * TODO: {@link #enqueueActivityJob} 以外のメソッドを持たないインターフェースを用意する
 */
public final class ActivityJobWorker {

    private final ActivityJobHandler handler = new ActivityJobHandler();

    public void enqueueActivityJob(ActivityJob job) {
        Message message = handler.obtainMessage();
        message.obj = job;
        handler.sendMessage(message);
    }

    public void setActivity(Activity activity) {
        this.handler.setActivity(activity);
    }

    public void resume() {
        handler.resume();
    }

    public void pause() {
        handler.pause();
    }
}
