package net.ichigotake.common.view;

import android.content.Context;
import android.view.View;

public final class ActionProvider extends android.view.ActionProvider {

    public static interface OnClickListener {
        void onClick();
    }

    private final OnClickListener listener;

    public ActionProvider(Context context, OnClickListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    public View onCreateActionView() {
        return null;
    }

    @Override
    public boolean onPerformDefaultAction() {
        listener.onClick();
        return false;
    }

}
