package circlebinder.common.view.carousel;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import circlebinder.R;

public class CarouselView extends RelativeLayout {

    private ViewPager pager;
    private PagerAdapter adapter;
    private ViewGroup indicatorsContainer;
    private ViewGroup backView;
    private ViewGroup forwardView;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private List<CarouselIndicator> indicators = new ArrayList<>();
    private CarouselIndicatorFactory carouselIndicatorFactory;

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
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_carousel, this);
        this.pager = (ViewPager) findViewById(R.id.view_carousel_pager);
        this.indicatorsContainer = (ViewGroup) findViewById(R.id.view_carousel_indicators);
        this.carouselIndicatorFactory = new DefaultCarouselIndicatorFactory(getContext());
        forwardView = (ViewGroup) findViewById(R.id.view_carousel_pager_forward);
        ViewGroup.LayoutParams forwardViewParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        );
        setForwardView(inflater.inflate(R.layout.view_carousel_forward, null), forwardViewParams);
        backView = (ViewGroup) findViewById(R.id.view_carousel_pager_back);
        ViewGroup.LayoutParams backViewParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        );
        setBackView(inflater.inflate(R.layout.view_carousel_back, null), backViewParams);
        this.pager.setOnPageChangeListener(new CarouselIndicatorListener());
    }

    public void setBackView(View backView, ViewGroup.LayoutParams params) {
        backView.setOnClickListener(v -> pager.setCurrentItem(pager.getCurrentItem()-1));
        this.backView.removeAllViews();
        this.backView.addView(backView, 0, params);
    }

    public void setForwardView(View forwardView, ViewGroup.LayoutParams params) {
        forwardView.setOnClickListener(v -> pager.setCurrentItem(pager.getCurrentItem()+1));
        this.forwardView.removeAllViews();
        this.forwardView.addView(forwardView, 0, params);
    }

    public void setAdapter(PagerAdapter adapter) {
        this.adapter = adapter;
        this.indicatorsContainer.removeAllViews();
        int size = adapter.getCount();
        indicators = carouselIndicatorFactory.create(size);
        for (CarouselIndicator item : indicators) {
            this.indicatorsContainer.addView(item.getView());
        }
        this.pager.setAdapter(adapter);
    }

    public void setIndicatorVisible(boolean visible) {
        indicatorsContainer.setVisibility(visible ? VISIBLE : GONE);
    }

    public void setCurrentItem(int position) {
        this.pager.setCurrentItem(position);
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        onPageChangeListener = listener;
    }

    public void setPageMargin(int pageMargin) {
        pager.setPageMargin(pageMargin);
    }

    public void setPageMarginDrawable(ColorDrawable drawable) {
        pager.setPageMarginDrawable(drawable);
    }

    private class CarouselIndicatorListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (onPageChangeListener != null) {
                onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {
            backView.setVisibility(position == 0 ? View.GONE : View.VISIBLE);

            int count = adapter != null ? adapter.getCount() : 0;
            int lastIndex = count-1;
            forwardView.setVisibility(position < lastIndex ? View.VISIBLE : View.GONE);
            for (int i=0; i<count; i++) {
                CarouselIndicator indicator = indicators.get(i);
                if (i == position) {
                    indicator.activate();
                } else {
                    indicator.deactivate();
                }
            }
            if (onPageChangeListener != null) {
                onPageChangeListener.onPageSelected(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (onPageChangeListener != null) {
                onPageChangeListener.onPageScrollStateChanged(state);
            }
        }
    }
}
