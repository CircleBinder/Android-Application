package net.ichigotake.common.util;

import android.view.View;

public class ViewFinder {

    private final View view;

    public ViewFinder(View view) {
        this.view = view;
    }

    @SuppressWarnings("unchecked")
    public <V extends View> Optional<V> find(int id) {
        return Optional.fromNullable((V) view.findViewById(id));
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V findOrNull(int id) {
        return (V) view.findViewById(id);
    }

    public ViewFinder into(int id) {
        return new ViewFinder(view.findViewById(id));
    }

}
