package net.ichigotake.common.app;

import android.content.Context;
import android.content.Intent;
import android.view.ActionProvider;
import android.view.View;

public final class OpenIntentActionProvider extends ActionProvider {

    private final Context context;
    private final Intent intent;

    public OpenIntentActionProvider(Context context, Intent intent) {
        super(context);
        this.context = context;
        this.intent = intent;
    }

    @Override
    public View onCreateActionView() {
        return null;
    }

    @Override
    public boolean onPerformDefaultAction() {
        new ActivityTripper(
                context,
                intent
        ).trip();
        return super.onPerformDefaultAction();
    }
}
