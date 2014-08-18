package circlebinder.common.view.carousel;

import android.widget.TextView;

final class CarouselIndicatorView implements CarouselIndicator {

    private final TextView indicator;

    CarouselIndicatorView(TextView textView) {
        this.indicator = textView;
    }

    @Override
    public void activate() {
        indicator.setText(" o ");
    }

    @Override
    public void deactivate() {
        indicator.setText(" - ");
    }
}
