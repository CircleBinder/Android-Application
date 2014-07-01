package circlebinder.creation.app.phone;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;

import net.ichigotake.common.app.ActivityNavigation;
import net.ichigotake.common.app.FragmentPagerAdapter;
import net.ichigotake.common.app.FragmentPagerItem;
import net.ichigotake.common.os.RestoreBundle;

import circlebinder.Legacy;
import circlebinder.common.event.Circle;
import circlebinder.common.search.CircleSearchOption;
import circlebinder.creation.BaseActivity;
import circlebinder.creation.R;
import circlebinder.creation.circle.CircleDetailFragment;
import circlebinder.creation.circle.CircleDetailViewHolder;
import circlebinder.creation.circle.OnCirclePageChangeListener;
import circlebinder.creation.event.CircleTable;
import circlebinder.creation.search.CircleCursorConverter;

public final class CircleDetailPagerActivity extends BaseActivity
        implements Legacy, OnCirclePageChangeListener {

    public static final int REQUEST_CODE_CALLBACK = 0;
    public static final String EXTRA_KEY_SEARCH_OPTION = "search_option";
    public static final String EXTRA_KEY_POSITION = "position";

    private FragmentPagerAdapter pagerAdapter;
    private CircleSearchOption searchOption;
    private int currentPosition;
    private ViewPager pager;
    private View headerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.circlebinder_activity_view_pager);

        headerView = findViewById(R.id.circlebinder_activity_view_pager_header);

        final RestoreBundle restoreBundle = new RestoreBundle(getIntent(), savedInstanceState);
        searchOption = restoreBundle.getParcelable(EXTRA_KEY_SEARCH_OPTION);
        currentPosition = restoreBundle.getInt(EXTRA_KEY_POSITION);

        pager = (ViewPager) findViewById(R.id.circlebinder_activity_view_pager);
        pagerAdapter = new FragmentPagerAdapter(
                getFragmentManager(),
                new CircleDetailPagerItem(searchOption)
        );
        pager.setAdapter(pagerAdapter);
        pager.setPageMargin(getResources().getDimensionPixelSize(
                R.dimen.circlebinder_spacer_small
        ));
        pager.setPageMarginDrawable(new ColorDrawable(
                getResources().getColor(R.color.circlebinder_app_card_container_background)
        ));
        final ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override public void onPageScrollStateChanged(int state) {}

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                pagerAdapter.reload(position);
                Bundle callbackArgs = restoreBundle.getBundle();
                callbackArgs.putInt(EXTRA_KEY_POSITION, position);
                Intent callbackIntent = getIntent();
                if (callbackIntent == null) {
                    callbackIntent = new Intent();
                }
                callbackIntent.putExtras(callbackArgs);
                setResult(REQUEST_CODE_CALLBACK, callbackIntent);
            }
        };
        pager.setOnPageChangeListener(onPageChangeListener);
        pagerAdapter.reload(currentPosition);
        pager.setCurrentItem(currentPosition);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onPageChangeListener.onPageSelected(currentPosition);
            }
        }, 1500);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return ActivityNavigation.back(this, menuItem)
                || super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (pager != null) {
            FragmentPagerAdapter pagerAdapter = (FragmentPagerAdapter) pager.getAdapter();
            pagerAdapter.reload(currentPosition);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_KEY_SEARCH_OPTION, searchOption);
        outState.putInt(EXTRA_KEY_POSITION, currentPosition);
    }

    @Override
    public void onCirclePageChanged(Circle circle) {
        CircleDetailViewHolder viewHolder = new CircleDetailViewHolder(headerView);
        viewHolder.getName().setText(circle.getPenName() + "/" + circle.getName());
        viewHolder.getSpace().setText(circle.getSpace().getName());
    }

    private static class CircleDetailPagerItem implements FragmentPagerItem {

        private final CircleCursorConverter cursorCreator;
        private final Cursor cursor;

        public CircleDetailPagerItem(CircleSearchOption circleSearchOption) {
            cursorCreator = new CircleCursorConverter();
            cursor = CircleTable.get(circleSearchOption);
        }

        @Override
        public CircleDetailFragment getItem(int position) {
            cursor.moveToPosition(position);
            Circle circle = cursorCreator.create(cursor);
            return CircleDetailFragment.factory(circle).create();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return cursor.getCount();
        }
    }
}
