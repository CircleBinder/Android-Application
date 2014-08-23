package circlebinder.common.view.carousel;

import android.view.View;

final class CarouseNavigationViewHolder {

    private final View forwardView;
    private final View backView;

    CarouseNavigationViewHolder(View forwardView, View backView) {
        this.forwardView = forwardView;
        this.backView = backView;
    }

    View getForwardView() {
        return forwardView;
    }

    View getBackView() {
        return backView;
    }
}
