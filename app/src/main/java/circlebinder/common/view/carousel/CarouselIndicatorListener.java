package circlebinder.common.view.carousel;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

final class CarouselIndicatorListener implements ViewPager.OnPageChangeListener {

    static CarouselIndicatorListener create(
            ViewPager viewPager,
            PagerAdapter adapter,
            CarouseNavigationViewHolder viewHolder,
            List<CarouselIndicator> indicators
    ) {
        viewHolder.getBackView().setOnClickListener(v -> viewPager.setCurrentItem(viewPager.getCurrentItem()-1));
        viewHolder.getForwardView().setOnClickListener(v -> viewPager.setCurrentItem(viewPager.getCurrentItem()+1));
        return new CarouselIndicatorListener(adapter, viewHolder, indicators);
    }
    private final PagerAdapter adapter;
    private final CarouseNavigationViewHolder viewHolder;
    private final List<CarouselIndicator> indicators;

    private CarouselIndicatorListener(
            PagerAdapter adapter, CarouseNavigationViewHolder viewHolder, List<CarouselIndicator> indicators
    ) {
        this.adapter = adapter;
        this.viewHolder = viewHolder;
        this.indicators = indicators;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        viewHolder.getBackView().setVisibility(position == 0 ? View.GONE : View.VISIBLE);

        int count = adapter.getCount();
        int lastIndex = count-1;
        viewHolder.getForwardView().setVisibility(position < lastIndex ? View.VISIBLE : View.GONE);
        for (int i=0; i<count; i++) {
            CarouselIndicator indicator = indicators.get(i);
            if (i == position) {
                indicator.activate();
            } else {
                indicator.deactivate();
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
