package net.ichigotake.common.view;

import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.view.View;

import net.ichigotake.common.content.ContentReloader;

public class ReloadActionProvider extends ActionProvider {

    private final ContentReloader reloader;

    public ReloadActionProvider(Context context, ContentReloader reloader) {
        super(context.getApplicationContext());
        this.reloader = reloader;
    }

    @Override
    public View onCreateActionView() {
        return null;
    }

    @Override
    public boolean onPerformDefaultAction() {
        reloader.reload();
        return false;
    }


}
