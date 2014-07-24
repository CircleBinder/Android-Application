package net.ichigotake.common.widget;

import android.view.View;
import android.widget.AdapterView;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class OnItemSelectedListener<T> implements AdapterView.OnItemSelectedListener {

    private final List<OnItemSelectedEventListener<T>> listeners;

    public OnItemSelectedListener() {
        this.listeners = new CopyOnWriteArrayList<OnItemSelectedEventListener<T>>();
    }

    public void addOnItemSelectedListener(OnItemSelectedEventListener<T> listener) {
        listeners.add(listener);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        for (OnItemSelectedEventListener<T> listener : listeners) {
            listener.onItemSelected((T)parent.getSelectedItem());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        for (OnItemSelectedEventListener<T> listener : listeners) {
            listener.onNothingSelected();
        }
    }
}
