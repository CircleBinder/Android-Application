package net.ichigotake.common.widget;

import android.util.Log;
import android.view.View;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class OnFocusChangeListeners implements View.OnFocusChangeListener {

    private final String LOG_TAG = OnFocusChangeListeners.class.getSimpleName();
    private final List<View.OnFocusChangeListener> listeners;

    public OnFocusChangeListeners() {
        this.listeners = new CopyOnWriteArrayList<View.OnFocusChangeListener>();
    }

    public void addOnFocusChangeListener(View.OnFocusChangeListener listener) {
        boolean hasListener = false;
        for (View.OnFocusChangeListener item : listeners) {
            if (item.equals(listener)) {
                hasListener = true;
            }
        }
        if (!hasListener) {
            listeners.add(listener);
        } else {
            Log.d(LOG_TAG, "Already added listener: " + listener.getClass().getName());
        }
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        for (View.OnFocusChangeListener listener : listeners) {
            listener.onFocusChange(v, hasFocus);
        }
    }
}
