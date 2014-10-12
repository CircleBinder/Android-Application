package circlebinder.creation.app.phone;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import net.ichigotake.common.app.ActivityNavigation;
import net.ichigotake.common.os.BundleMerger;
import net.ichigotake.common.view.ActionProvider;
import net.ichigotake.common.worker.ActivityJobWorker;

import circlebinder.common.event.Block;
import circlebinder.common.search.CircleSearchOption;
import circlebinder.common.search.CircleSearchOptionBuilder;
import circlebinder.common.search.OnCircleSearchOptionListener;
import circlebinder.creation.app.BaseActivity;
import circlebinder.R;
import circlebinder.creation.search.BlockSelectorFragment;
import circlebinder.creation.search.InputKeywordView;
import circlebinder.creation.search.OnBlockSelectListener;
import circlebinder.creation.search.SearchFormStore;

public final class CircleSearchActivity extends BaseActivity implements OnBlockSelectListener {

    public static Intent createIntent(Context context) {
        return new Intent(context, CircleSearchActivity.class);
    }

    private final ActivityJobWorker worker = new ActivityJobWorker();
    private final String EXTRA_CIRCLE_SEARCH_BUILDER = "circle_search_builder";
    private CircleSearchOptionBuilder searchOptionBuilder;
    private SearchFormStore searchFormStore;
    private InputKeywordView inputKeywordView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_search);
        worker.setActivity(this);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        searchOptionBuilder = BundleMerger.merge(getIntent(), savedInstanceState)
                .getParcelable(EXTRA_CIRCLE_SEARCH_BUILDER);
        if (searchOptionBuilder == null) {
            searchOptionBuilder = new CircleSearchOptionBuilder();
        }

        inputKeywordView = (InputKeywordView) findViewById(R.id.activity_circle_search_option_keyword);
        inputKeywordView.setOnInputTextListener(keyword -> {
            searchOptionBuilder.setKeyword(keyword);
            setSearchOption(searchOptionBuilder.build());
        });
        updateKeyword();
        searchFormStore = new SearchFormStore(getApplicationContext());
        if (searchFormStore.isFormVisible()) {
            showForm();
        } else {
            hideForm();
        }

        View actionBarView = getLayoutInflater().inflate(R.layout.action_bar_circle_search_option, null);
        BlockSelectorFragment blockSelectorFragment = (BlockSelectorFragment) getFragmentManager()
                .findFragmentById(R.id.action_bar_circle_search_option);
        blockSelectorFragment.setSelection(searchOptionBuilder.build().getBlock());
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(actionBarView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        searchItem.setActionProvider(new ActionProvider(getApplicationContext(), this::showForm));
        inflater.inflate(R.menu.clear, menu);
        MenuItem hiddenItem = menu.findItem(R.id.menu_cancel)
                .setActionProvider(new ActionProvider(getApplicationContext(), this::hideForm));
        if (searchFormStore.isFormVisible()) {
            searchItem.setVisible(false);
            hiddenItem.setVisible(true);
            inputKeywordView.requestFocus();
        } else {
            searchItem.setVisible(true);
            hiddenItem.setVisible(false);
            inputKeywordView.clearFocus();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return ActivityNavigation.back(this, menuItem)
                || super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onResume() {
        super.onResume();
        worker.resume();
    }

    @Override
    public void onPause() {
        worker.pause();
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_CIRCLE_SEARCH_BUILDER, searchOptionBuilder);
    }

    private void setSearchOption(CircleSearchOption searchOption) {
        Fragment fragment = getFragmentManager().findFragmentById(R.id.activity_circle_search_circles_container);
        if (fragment != null && fragment.isResumed() && fragment instanceof OnCircleSearchOptionListener) {
            ((OnCircleSearchOptionListener)fragment).setSearchOption(searchOption);
        }
    }

    private void showForm() {
        searchFormStore.setFormVisible(true);
        inputKeywordView.setVisibility(View.VISIBLE);
        invalidateOptionsMenu();
    }

    private void hideForm() {
        searchFormStore.setFormVisible(false);
        inputKeywordView.setVisibility(View.GONE);
        setSearchOption(searchOptionBuilder.setKeyword("").build());
        updateKeyword();
        invalidateOptionsMenu();
    }

    private void updateKeyword() {
        inputKeywordView.setText(searchOptionBuilder.build().getKeyword());
    }

    @Override
    public void onBlockSelect(Block block) {
        searchOptionBuilder.setBlock(block);
        setSearchOption(searchOptionBuilder.build());
    }
}
