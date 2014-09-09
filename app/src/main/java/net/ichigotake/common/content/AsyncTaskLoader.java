package net.ichigotake.common.content;

import android.content.Context;

public abstract class AsyncTaskLoader<D> extends android.content.AsyncTaskLoader<D> {

    private D _data;

    public AsyncTaskLoader(Context context) {
        super(context);
    }

    @Override
    public void deliverResult(D data) {
        if (isReset()) {
            return;
        }
        _data = data;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        if (_data != null) {
            deliverResult(_data);
        }

        if (takeContentChanged() || _data == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        _data = null;
    }
}
