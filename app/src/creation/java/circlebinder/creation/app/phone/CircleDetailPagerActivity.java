package circlebinder.creation.app.phone;

import android.app.ActionBar;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;

import net.ichigotake.common.app.ActivityNavigation;
import net.ichigotake.common.app.FragmentPagerAdapter;
import net.ichigotake.common.app.FragmentPagerItemCreator;
import net.ichigotake.common.os.BundleMerger;

import circlebinder.common.Legacy;
import circlebinder.common.checklist.ChecklistColor;
import circlebinder.common.checklist.ChecklistPopupSelector;
import circlebinder.common.event.Circle;
import circlebinder.common.event.CircleBuilder;
import circlebinder.common.search.CircleSearchOption;
import circlebinder.creation.app.BaseActivity;
import circlebinder.R;
import circlebinder.creation.circle.CircleDetailFragment;
import circlebinder.creation.circle.CircleDetailViewHolder;
import circlebinder.creation.circle.OnCirclePageChangeListener;
import circlebinder.creation.event.CircleTable;
import circlebinder.creation.search.CircleCursorConverter;
import circlebinder.creation.search.CircleLoader;

public final class CircleDetailPagerActivity extends BaseActivity
        implements Legacy, OnCirclePageChangeListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String EXTRA_KEY_SEARCH_OPTION = "search_option";
    private static final String EXTRA_KEY_POSITION = "position";

    public static Intent createIntent(Context context, CircleSearchOption searchOption, int position) {
        Intent intent = new Intent(context, CircleDetailPagerActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(EXTRA_KEY_SEARCH_OPTION, searchOption);
        extras.putInt(EXTRA_KEY_POSITION, position);
        intent.putExtras(extras);
        return intent;
    }

    private FragmentPagerAdapter pagerAdapter;
    private CircleSearchOption searchOption;
    private int currentPosition;
    private CircleDetailViewHolder headerViewHolder;
    private CircleDetailViewHolder actionBarViewHolder;
    private ViewPager pager;
    private View checklistColorView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_circle_detail_pager);

        checklistColorView = findViewById(R.id.activity_circle_detail_pager_checklist);
        final ChecklistPopupSelector checklistSelector =
                new ChecklistPopupSelector(this, checklistColorView);
        checklistColorView.setOnClickListener(v -> {
            if (checklistSelector.isShowing()) {
                checklistSelector.dismiss();
            } else {
                checklistSelector.show();
            }
        });
        checklistSelector.setOnItemClickListener(item -> {
            updateChecklistColor(item);
            checklistSelector.dismiss();
        });

        headerViewHolder = new CircleDetailViewHolder(findViewById(R.id.activity_circle_detail_pager_header));
        actionBar.setCustomView(CircleDetailViewHolder.layoutResource);
        actionBarViewHolder = new CircleDetailViewHolder(actionBar.getCustomView());

        Bundle bundle = BundleMerger.merge(getIntent(), savedInstanceState);
        searchOption = bundle.getParcelable(EXTRA_KEY_SEARCH_OPTION);
        currentPosition = bundle.getInt(EXTRA_KEY_POSITION);

        pager = (ViewPager) findViewById(R.id.activity_circle_detail_pager);
        pager.setPageMargin(getResources().getDimensionPixelSize(
                R.dimen.common_spacer_x_small
        ));
        pager.setPageMarginDrawable(new ColorDrawable(
                getResources().getColor(R.color.common_card_container_background)
        ));
        orientationConfig(getResources().getConfiguration());
        getLoaderManager().initLoader(0, bundle, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return ActivityNavigation.back(this, menuItem)
                || super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (pagerAdapter != null && pager != null) {
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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        orientationConfig(newConfig);
    }

    @Override
    public void onCirclePageChanged(Circle circle) {
        ChecklistColor checklistColor = circle.getChecklistColor();
        updateChecklistColor(checklistColor);
        CircleTable.setChecklist(circle, checklistColor);
        circle = new CircleBuilder(circle)
                .setChecklistColor(checklistColor)
                .build();
        actionBarViewHolder.getName().setText(circle.getPenName() + "/" + circle.getName());
        actionBarViewHolder.getSpace().setText(circle.getSpace().getName());
        headerViewHolder.getName().setText(circle.getPenName() + "/" + circle.getName());
        headerViewHolder.getSpace().setText(circle.getSpace().getName());
        orientationConfig(getResources().getConfiguration());
        invalidateOptionsMenu();
    }

    private void updateChecklistColor(ChecklistColor checklistColor) {
        checklistColorView.setBackgroundResource(checklistColor.getDrawableResource());
    }

    private void orientationConfig(Configuration configuration) {
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getActionBar().setDisplayShowCustomEnabled(true);
            headerViewHolder.getContainer().setVisibility(View.GONE);
        } else {
            getActionBar().setDisplayShowCustomEnabled(false);
            headerViewHolder.getContainer().setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CircleLoader(getApplicationContext(), searchOption);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        pagerAdapter = new FragmentPagerAdapter(
                getFragmentManager(),
                new CircleDetailPagerItemCreator(data)
        );
        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override public void onPageScrollStateChanged(int state) {}
            @Override public void onPageSelected(int position) {
                currentPosition = position;
                pagerAdapter.reload(position);
            }
        };
        pager.setOnPageChangeListener(onPageChangeListener);
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(currentPosition);
        new Handler().postDelayed(() -> onPageChangeListener.onPageSelected(currentPosition), 1000);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private static class CircleDetailPagerItemCreator implements FragmentPagerItemCreator {

        private final CircleCursorConverter cursorCreator;
        private final Cursor cursor;

        public CircleDetailPagerItemCreator(Cursor cursor) {
            this.cursorCreator = new CircleCursorConverter();
            this.cursor = cursor;
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
