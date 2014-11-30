package net.ichigotake.common.view;

import android.view.View;

public final class OnClickToToggleView implements View.OnClickListener {

    private final View target;

    public OnClickToToggleView(View target) {
        this.target = target;
    }

    @Override
    public void onClick(View v) {
        if (target.isShown()) {
            target.setVisibility(View.GONE);
        } else {
            target.setVisibility(View.VISIBLE);
        }
    }
}
