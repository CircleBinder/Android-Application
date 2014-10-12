package net.ichigotake.common.app;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public final class OnClickToTrip implements View.OnClickListener {

    public static OnClickToTrip activityTrip(Context context, Intent intent) {
        return new OnClickToTrip(new ActivityTripper(context, intent));
    }

    private final Tripper tripper;

    public OnClickToTrip(Tripper tripper) {
        this.tripper = tripper;
    }

    @Override
    public void onClick(View v) {
        tripper.trip();
    }
}