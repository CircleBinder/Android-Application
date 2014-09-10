package circlebinder.common.web;

import android.content.Context;
import android.view.ActionProvider;
import android.view.View;

import net.ichigotake.common.content.ContentReloader;

public class ReloadWebViewActionProvider extends ActionProvider {

    private final ContentReloader reloader;

    public ReloadWebViewActionProvider(Context context, ContentReloader reloader) {
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
