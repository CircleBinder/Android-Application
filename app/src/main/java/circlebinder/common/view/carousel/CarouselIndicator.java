package circlebinder.common.view.carousel;

import android.view.View;

interface CarouselIndicator {

    View getView();

    void activate();

    void deactivate();
}
