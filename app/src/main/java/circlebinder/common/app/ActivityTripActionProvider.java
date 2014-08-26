package circlebinder.common.app;

import android.content.Context;
import android.content.Intent;
import android.view.ActionProvider;
import android.view.View;

import net.ichigotake.common.app.ActivityTripper;
import net.ichigotake.common.app.Tripper;

public final class ActivityTripActionProvider extends ActionProvider {

    private final Context context;
    private final Intent intent;

    public ActivityTripActionProvider(Context context, Intent intent) {
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
        new ActivityTripper(context, intent).trip();
        return true;
    }
}
