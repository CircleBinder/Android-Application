package circlebinder.common.view.carousel;

import android.view.View;
import android.widget.TextView;

final class DefaultCarouselIndicator implements CarouselIndicator {

    private final TextView indicator;

    DefaultCarouselIndicator(TextView textView) {
        this.indicator = textView;
    }

    @Override
    public View getView() {
        return indicator;
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
