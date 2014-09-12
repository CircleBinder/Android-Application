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
import android.widget.Spinner;

import net.ichigotake.common.app.ActivityNavigation;
import net.ichigotake.common.os.BundleMerger;
import net.ichigotake.common.view.ActionProvider;
import net.ichigotake.common.worker.ActivityJobWorker;

import circlebinder.common.checklist.BlockSelectorContainer;
import circlebinder.common.search.CircleSearchOption;
import circlebinder.common.search.CircleSearchOptionBuilder;
import circlebinder.common.search.OnCircleSearchOptionListener;
import circlebinder.creation.app.BaseActivity;
import circlebinder.R;
import circlebinder.creation.search.GenreSelectorContainer;
import circlebinder.creation.search.InputTextFragment;
import circlebinder.creation.search.OnInputTextListener;
import circlebinder.creation.search.SearchFormStore;

public final class CircleSearchActivity extends BaseActivity
        implements OnInputTextListener {

    public static Intent createIntent(Context context) {
        return new Intent(context, CircleSearchActivity.class);
    }

    private final ActivityJobWorker worker = new ActivityJobWorker();
    private final String EXTRA_CIRCLE_SEARCH_BUILDER = "circle_search_builder";
    private CircleSearchOptionBuilder searchOptionBuilder;
    private SearchFormStore searchFormStore;
    private View textInputContainer;

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

        textInputContainer = findViewById(R.id.activity_circle_search_option_keyword_container);
        updateKeyword();
        searchFormStore = new SearchFormStore(getApplicationContext());
        if (searchFormStore.isFormVisible()) {
            showForm();
        } else {
            hideForm();
        }

        View actionBarView = getLayoutInflater().inflate(R.layout.circle_search_option, null);
        BlockSelectorContainer genreSelectorContainer = GenreSelectorContainer.init(
                getApplicationContext(),
                (Spinner) actionBarView.findViewById(R.id.circle_search_option_block_selector
                ));
        genreSelectorContainer.addOnItemSelectedListener(item -> {
            searchOptionBuilder.setBlock(item);
            setSearchOption(searchOptionBuilder.build());
        });
        genreSelectorContainer.setSelection(searchOptionBuilder.build().getBlock());
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
        } else {
            searchItem.setVisible(true);
            hiddenItem.setVisible(false);
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

    private void setSearchOption(CircleSearchOption searchOption) {
        Fragment fragment = getFragmentManager().findFragmentById(R.id.activity_circle_search_circles_container);
        if (fragment != null && fragment.isResumed() && fragment instanceof OnCircleSearchOptionListener) {
            ((OnCircleSearchOptionListener)fragment).setSearchOption(searchOption);
        }
    }

    @Override
    public void onTextChange(String keyword) {
        searchOptionBuilder.setKeyword(keyword);
        setSearchOption(searchOptionBuilder.build());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_CIRCLE_SEARCH_BUILDER, searchOptionBuilder);

    }

    private void showForm() {
        searchFormStore.setFormVisible(true);
        textInputContainer.setVisibility(View.VISIBLE);
        invalidateOptionsMenu();
    }

    private void hideForm() {
        searchFormStore.setFormVisible(false);
        textInputContainer.setVisibility(View.GONE);
        setSearchOption(searchOptionBuilder.setKeyword("").build());
        updateKeyword();
        invalidateOptionsMenu();
    }

    private void updateKeyword() {
        ((InputTextFragment)getFragmentManager()
                .findFragmentById(R.id.activity_circle_search_option_keyword_container))
                .setText(searchOptionBuilder.build().getKeyword());
    }

}
