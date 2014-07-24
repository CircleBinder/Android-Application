package net.ichigotake.common.os;

import android.os.AsyncTask;

public final class RunnableAsyncTask extends AsyncTask<Runnable, Void, Void> {

    @Override
    protected Void doInBackground(Runnable... params) {
        for (Runnable runnable : params) {
            runnable.run();
        }
        return null;
    }
}
