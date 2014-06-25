package circlebinder.creation.app.phone;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;

import net.ichigotake.common.app.ActivityFactory;
import net.ichigotake.common.app.ActivityTripper;
import net.ichigotake.common.app.FragmentOnPageChangeListener;
import net.ichigotake.common.app.FragmentPagerAdapter;
import net.ichigotake.common.app.FragmentPagerItem;
import net.ichigotake.common.os.RestoreBundle;

import circlebinder.Legacy;
import circlebinder.common.app.ViewPagerStateStore;
import circlebinder.creation.ApplicationPreferenceKey;
import circlebinder.creation.BaseActivity;
import circlebinder.creation.R;
import circlebinder.creation.checklist.ChecklistFragment;
import circlebinder.creation.initialize.AppStorage;
import circlebinder.creation.navigation.NavigationDrawerFragment;
import circlebinder.creation.search.CircleSearchFragment;

/**
 * 通常起動時のファーストビュー
 */
public final class HomeActivity extends BaseActivity implements Legacy {

    public static ActivityFactory from() {
        return new HomeActivityFactory();
    }

    public static ActivityTripper tripper(Context context) {
        return new ActivityTripper(context, from());
    }

    private static class HomeActivityFactory implements ActivityFactory {

        @Override
        public Intent create(Context context) {
            return new Intent(context, HomeActivity.class);
        }
    }

    private final String KEY_CURRENT_PAGE_ITEM = "current_page_item";
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //役割的にはアクティビティを分けた方がいいとは思う
        //でも普段の起動では少しでもシームレス感を出したい
        //ので、アクティビティ分割をせずにこの条件分岐はこのままにしておく
        if (new AppStorage(getApplicationContext()).isInitialized()) {
            setContentView(R.layout.circlebinder_activity_home);

            mNavigationDrawerFragment = (NavigationDrawerFragment)
                    getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM, ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.circlebinder_actionbar_pagr_tab);
            mTitle = actionBar.getTitle();
            mNavigationDrawerFragment.setUp(
                    R.id.navigation_drawer,
                    (DrawerLayout) findViewById(R.id.drawer_layout));

            ViewPager pager = getPager();
            int presetPageNo = new RestoreBundle(getIntent(), savedInstanceState)
                    .getInt(KEY_CURRENT_PAGE_ITEM);
            pager.setCurrentItem(presetPageNo);
            final FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(
                    getSupportFragmentManager(),
                    new HomePagerItem()
            );
            pager.setPageMargin(getResources().getDimensionPixelSize(
                    R.dimen.circlebinder_spacer_small
            ));
            pager.setPageMarginDrawable(new ColorDrawable(
                    getResources().getColor(R.color.circlebinder_app_card_container_background)
            ));
            pager.setAdapter(pagerAdapter);
            PagerSlidingTabStrip strip =
                    (PagerSlidingTabStrip)actionBar.getCustomView().findViewById(R.id.activity_home_tab);
            strip.setShouldExpand(true);

            //TODO: リソースで指定するべき
            strip.setIndicatorHeight(8);
            strip.setUnderlineHeight(0);

            //TODO: 色はリソースに置こう
            strip.setIndicatorColor(0xffffffff);
            strip.setUnderlineColor(0xffffffff);
            strip.setViewPager(pager);
            strip.setTextColor(0xffffffff);

            pager.setOnPageChangeListener(
                    new FragmentOnPageChangeListener(pagerAdapter, presetPageNo)
            );
        } else {
            DatabaseInitializeActivity.tripper(this)
                    .withFinish()
                    .trip();
        }
    }

    private ViewPagerStateStore viewPagerStateStore;

    private ViewPagerStateStore getViewPagerStateStore(Context context) {
        if (viewPagerStateStore == null) {
            viewPagerStateStore = new ViewPagerStateStore(context, ApplicationPreferenceKey.HOME_PAGER_STATE);
        }
        return viewPagerStateStore;
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewPager pager = getPager();
        if (pager != null) {
            pager.setCurrentItem(getViewPagerStateStore(this).getItemPosition());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ViewPager pager = getPager();
        if (pager != null) {
            getViewPagerStateStore(this).setCurrentItem(pager.getCurrentItem());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState == null && getPager() != null) {
            outState = new Bundle();
            outState.putInt(KEY_CURRENT_PAGE_ITEM, getPager().getCurrentItem());
        }
        super.onSaveInstanceState(outState);
    }

    private ViewPager getPager() {
        return (ViewPager)findViewById(R.id.activity_home_pager);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mNavigationDrawerFragment != null && !mNavigationDrawerFragment.isDrawerOpen()) {
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private static class HomePagerItem implements FragmentPagerItem {

        @Override
        public Fragment getItem(int position) {
            Fragment item;
            switch (position) {
                case 0:
                    item = ChecklistFragment.factory().create();
                    break;
                case 1:
                    item = CircleSearchFragment.factory().create();
                    break;
                default:
                    item = null;
            }
            return item;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            CharSequence pageTitle;
            switch (position) {
                case 0:
                    pageTitle = "お気に入り";
                    break;
                case 1:
                    pageTitle = "検索";
                    break;
                default:
                    pageTitle = "";
            }
            return pageTitle;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
