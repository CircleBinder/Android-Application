package net.ichigotake.common.app;

import android.view.View;

public final class OnClickToTrip implements View.OnClickListener {

    private final Tripper tripper;

    public OnClickToTrip(Tripper tripper) {
        this.tripper = tripper;
    }

    @Override
    public void onClick(View v) {
        tripper.trip();
    }
}