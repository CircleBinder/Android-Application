package net.ichigotake.common.widget;

import android.view.View;
import android.widget.AdapterView;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class OnItemClickListener<T> implements AdapterView.OnItemClickListener {

    private final List<OnItemClickEventListener<T>> listeners;

    public OnItemClickListener() {
        listeners = new CopyOnWriteArrayList<OnItemClickEventListener<T>>();
    }

    public void addOnItemClickEventListener(OnItemClickEventListener<T> listener) {
        listeners.add(listener);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        for (OnItemClickEventListener<T> listener : listeners) {
            listener.onItemClick((T)parent.getItemAtPosition(position));
        }
    }
}
