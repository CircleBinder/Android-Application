package circlebinder.common.view.carousel;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import circlebinder.R;

class DefaultCarouselIndicatorFactory implements CarouselIndicatorFactory {

    private final LayoutInflater inflater;

    DefaultCarouselIndicatorFactory(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public List<CarouselIndicator> create(int size) {
        List<CarouselIndicator> indicators = new ArrayList<>();
        for (int i=0; i<size; i++) {
            TextView indicatorView = (TextView) inflater.inflate(R.layout.view_carousel_indicator, null);
            CarouselIndicator indicator = new DefaultCarouselIndicator(indicatorView);
            if (i == 0) {
                indicator.activate();
            } else {
                indicator.deactivate();
            }
            indicators.add(indicator);
        }
        return indicators;
    }
}
