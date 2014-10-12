package circlebinder.creation.app.phone;

import android.app.ActionBar;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;

import net.ichigotake.common.app.ActivityNavigation;
import net.ichigotake.common.os.BundleMerger;

import circlebinder.common.Legacy;
import circlebinder.common.app.FragmentTripper;
import circlebinder.common.event.Circle;
import circlebinder.common.search.CircleSearchOption;
import circlebinder.creation.app.BaseActivity;
import circlebinder.R;
import circlebinder.creation.circle.CircleDetailFragment;
import circlebinder.creation.circle.CircleDetailViewHolder;
import circlebinder.creation.circle.OnCirclePageChangeListener;
import circlebinder.creation.search.CircleCursorConverter;
import circlebinder.creation.search.CircleLoader;

public final class CircleDetailActivity extends BaseActivity
        implements Legacy, OnCirclePageChangeListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String EXTRA_KEY_SEARCH_OPTION = "search_option";
    private static final String EXTRA_KEY_POSITION = "position";

    public static Intent createIntent(Context context, CircleSearchOption searchOption, int position) {
        Intent intent = new Intent(context, CircleDetailActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(EXTRA_KEY_SEARCH_OPTION, searchOption);
        extras.putInt(EXTRA_KEY_POSITION, position);
        intent.putExtras(extras);
        return intent;
    }

    private CircleSearchOption searchOption;
    private int currentPosition;
    private CircleDetailViewHolder headerViewHolder;
    private CircleDetailViewHolder actionBarViewHolder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_circle_detail_pager);

        headerViewHolder = new CircleDetailViewHolder(findViewById(R.id.activity_circle_detail_header));
        actionBar.setCustomView(CircleDetailViewHolder.layoutResource);
        actionBarViewHolder = new CircleDetailViewHolder(actionBar.getCustomView());

        Bundle bundle = BundleMerger.merge(getIntent(), savedInstanceState);
        searchOption = bundle.getParcelable(EXTRA_KEY_SEARCH_OPTION);
        currentPosition = bundle.getInt(EXTRA_KEY_POSITION);

        orientationConfig(getResources().getConfiguration());
        getLoaderManager().initLoader(0, bundle, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return ActivityNavigation.back(this, menuItem)
                || super.onOptionsItemSelected(menuItem);
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
        actionBarViewHolder.getName().setText(circle.getPenName() + "/" + circle.getName());
        actionBarViewHolder.getSpace().setText(circle.getSpace().getName());
        headerViewHolder.getName().setText(circle.getPenName() + "/" + circle.getName());
        headerViewHolder.getSpace().setText(circle.getSpace().getName());
        orientationConfig(getResources().getConfiguration());
        invalidateOptionsMenu();
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
        data.moveToPosition(currentPosition);
        final Circle circle = new CircleCursorConverter().create(data);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                new FragmentTripper(getFragmentManager(), CircleDetailFragment.factory(circle))
                        .setAddBackStack(false)
                        .setLayoutId(R.id.activity_circle_detail_item)
                        .trip();
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
