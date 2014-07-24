package circlebinder.common.app;

import android.content.Context;
import android.view.ActionProvider;
import android.view.View;

import net.ichigotake.common.app.Tripper;

public final class TripActionProvider extends ActionProvider {

    private final Tripper tripper;

    public TripActionProvider(Context context, Tripper tripper) {
        super(context);
        this.tripper = tripper;
    }

    @Override
    public View onCreateActionView() {
        return null;
    }

    @Override
    public boolean onPerformDefaultAction() {
        tripper.trip();
        return true;
    }
}
