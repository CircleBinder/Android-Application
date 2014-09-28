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
    private static final String EXTRA_KEY_ON_FIRST_LOAD = "on_first_load";

    public static Intent createIntent(Context context, CircleSearchOption searchOption, int position) {
        Intent intent = new Intent(context, CircleDetailPagerActivity.class);
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
    private View checklistColorView;
    private boolean onFirstLoad;

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
        onFirstLoad = bundle.getBoolean(EXTRA_KEY_ON_FIRST_LOAD, true);

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
        outState.putBoolean(EXTRA_KEY_ON_FIRST_LOAD, onFirstLoad);
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
        new Handler(Looper.getMainLooper()).post(() -> {
            data.moveToPosition(currentPosition);
            Circle circle = new CircleCursorConverter().create(data);
            new FragmentTripper(getFragmentManager(), CircleDetailFragment.factory(circle))
                    .setAddBackStack(!onFirstLoad)
                    .setLayoutId(R.id.activity_circle_detail_pager_item)
                    .trip();
            onFirstLoad = false;
        });
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
