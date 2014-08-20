package circlebinder.common.view.carousel;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import circlebinder.R;

public class CarouselView extends RelativeLayout {

    private LayoutInflater inflater;
    private ViewPager pager;
    private ViewGroup indicatorsContainer;
    private CarouseNavigationViewHolder viewHolder;

    public CarouselView(Context context) {
        super(context);
        initialize();
    }

    public CarouselView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public CarouselView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        this.inflater = LayoutInflater.from(getContext());
        this.inflater.inflate(R.layout.view_carousel, this);
        this.pager = (ViewPager) findViewById(R.id.view_carousel_pager);
        this.indicatorsContainer = (ViewGroup) findViewById(R.id.view_carousel_indicators);
        this.viewHolder = new CarouseNavigationViewHolder(
                findViewById(R.id.view_carousel_pager_forward),
                findViewById(R.id.view_carousel_pager_back)
        );
    }

    public void setAdapter(PagerAdapter adapter) {
        this.indicatorsContainer.removeAllViews();
        int count = adapter.getCount();
        List<CarouselIndicator> indicators = new ArrayList<>();
        for (int i=0; i<count; i++) {
            TextView indicatorView = (TextView)
                    this.inflater.inflate(R.layout.view_carousel_indicator, null);
            this.indicatorsContainer.addView(indicatorView);
            CarouselIndicator indicator = new CarouselIndicatorView(indicatorView);
            if (i == 0) {
                indicator.activate();
            } else {
                indicator.deactivate();
            }
            indicators.add(indicator);
        }
        this.pager.setAdapter(adapter);
        this.pager.setOnPageChangeListener(
                CarouselIndicatorListener.create(pager, adapter, viewHolder, indicators)
        );
    }

    public void setCurrentItem(int position) {
        this.pager.setCurrentItem(position);
    }
}
