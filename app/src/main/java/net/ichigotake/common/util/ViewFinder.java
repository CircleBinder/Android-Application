package net.ichigotake.common.util;

import android.view.View;

public class ViewFinder {

    private final View view;

    public ViewFinder(View view) {
        this.view = view;
    }

    public <V extends View> V find(int id) {
        return (V) view.findViewById(id);
    }

    public ViewFinder into(int id) {
        return new ViewFinder(view.findViewById(id));
    }

}
