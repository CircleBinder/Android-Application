package circlebinder.creation.app.phone;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import net.ichigotake.common.app.ActivityFactory;
import net.ichigotake.common.app.ActivityNavigation;
import net.ichigotake.common.app.ActivityTripper;
import net.ichigotake.common.app.FragmentPagerAdapter;
import net.ichigotake.common.app.FragmentPagerItem;
import net.ichigotake.common.os.RestoreBundle;

import circlebinder.Legacy;
import circlebinder.common.event.Circle;
import circlebinder.common.search.CircleSearchOption;
import circlebinder.creation.BaseActivity;
import circlebinder.creation.R;
import circlebinder.creation.circle.CircleDetailFragment;
import circlebinder.creation.event.CircleTable;
import circlebinder.creation.search.CircleCursorCreator;

/**
 * TODO: Activityを閉じた時に元の画面にpositionを渡したい
 */
public final class CircleDetailPagerActivity extends BaseActivity implements Legacy {

    public static ActivityTripper tripper(Context context, CircleSearchOption searchOption,
                                          int position) {
        return new ActivityTripper(context, factory(searchOption, position));
    }

    public static ActivityFactory factory(CircleSearchOption circleSearchOption, int currentPosition) {
        return new CircleDetailPagerActivityFactory(circleSearchOption, currentPosition);
    }

    private static class CircleDetailPagerActivityFactory implements ActivityFactory {

        private final CircleSearchOption searchOption;
        private final int currentPosition;

        private CircleDetailPagerActivityFactory(CircleSearchOption circleSearchOption, int currentPosition) {
            searchOption = circleSearchOption;
            this.currentPosition = currentPosition;
        }

        @Override
        public Intent create(Context context) {
            Intent intent =  new Intent(context, CircleDetailPagerActivity.class);
            intent.putExtra(KEY_SEARCH_OPTION, searchOption);
            intent.putExtra(KEY_CURRENT_POSITION, currentPosition);
            return intent;
        }
    }

    private static final String KEY_SEARCH_OPTION = "searchOption";
    private static final String KEY_CURRENT_POSITION = "currentPosition";
    private CircleSearchOption searchOption;
    private int currentPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restoreActionBar();
        setContentView(R.layout.circlebinder_activity_view_pager);

        RestoreBundle restoreBundle = new RestoreBundle(getIntent(), savedInstanceState);
        searchOption = restoreBundle.getParcelable(KEY_SEARCH_OPTION);
        currentPosition = restoreBundle.getInt(KEY_CURRENT_POSITION);

        final ViewPager pager = (ViewPager) findViewById(R.id.circlebinder_activity_view_pager);
        final FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(
                getSupportFragmentManager(),
                new CircleDetailPagerItem(searchOption)
        );
        pager.setAdapter(pagerAdapter);
        pager.setPageMargin(getResources().getDimensionPixelSize(
                R.dimen.circlebinder_spacer_small
        ));
        pager.setPageMarginDrawable(new ColorDrawable(
                getResources().getColor(R.color.circlebinder_app_card_container_background)
        ));
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override public void onPageScrollStateChanged(int state) {}

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                pagerAdapter.reload(position);
            }
        });
        pagerAdapter.reload(currentPosition);
        pager.setCurrentItem(currentPosition);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return ActivityNavigation.back(this, menuItem)
                || super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onResume() {
        super.onResume();
        restoreActionBar();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState == null) {
            outState = new Bundle();
        }
        outState.putParcelable(KEY_SEARCH_OPTION, searchOption);
        outState.putInt(KEY_CURRENT_POSITION, currentPosition);
        super.onSaveInstanceState(outState);
    }
    
    private void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private static class CircleDetailPagerItem implements FragmentPagerItem {

        private final CircleCursorCreator cursorCreator;
        private final Cursor cursor;

        public CircleDetailPagerItem(CircleSearchOption circleSearchOption) {
            cursorCreator = new CircleCursorCreator();
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
