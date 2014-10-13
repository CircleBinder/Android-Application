package circlebinder.common.app.phone;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import net.ichigotake.common.app.ActivityNavigation;
import net.ichigotake.common.content.ContentReloader;
import net.ichigotake.common.os.BundleMerger;
import net.ichigotake.common.view.ActionProvider;
import net.ichigotake.common.widget.OnItemSelectedEventListener;
import net.ichigotake.common.worker.ActivityJobWorker;

import circlebinder.common.app.BroadcastEvent;
import circlebinder.common.checklist.EventBlockSelectorView;
import circlebinder.common.event.Block;
import circlebinder.common.search.CircleSearchOption;
import circlebinder.common.search.CircleSearchOptionBuilder;
import circlebinder.common.search.InputKeywordView;
import circlebinder.common.search.OnCircleSearchOptionListener;
import circlebinder.common.app.BaseActivity;
import circlebinder.R;
import circlebinder.common.search.OnInputTextListener;
import circlebinder.common.search.SearchFormStore;
import circlebinder.common.table.EventBlockTable;

public final class CircleSearchActivity extends BaseActivity {

    public static Intent createIntent(Context context) {
        return new Intent(context, CircleSearchActivity.class);
    }

    private final ActivityJobWorker worker = new ActivityJobWorker();
    private final String EXTRA_CIRCLE_SEARCH_BUILDER = "circle_search_builder";
    private CircleSearchOptionBuilder searchOptionBuilder;
    private SearchFormStore searchFormStore;
    private InputKeywordView inputKeywordView;
    private BroadcastReceiver broadcastReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_circle_search);
        worker.setActivity(this);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        searchOptionBuilder = BundleMerger.merge(getIntent(), savedInstanceState)
                .getParcelable(EXTRA_CIRCLE_SEARCH_BUILDER);
        if (searchOptionBuilder == null) {
            searchOptionBuilder = new CircleSearchOptionBuilder();
        }

        inputKeywordView = (InputKeywordView) findViewById(R.id.common_activity_circle_search_option_keyword);
        inputKeywordView.setOnInputTextListener(new OnInputTextListener() {
            @Override
            public void onTextChange(String keyword) {
                searchOptionBuilder.setKeyword(keyword);
                setSearchOption(searchOptionBuilder.build());
            }
        });
        updateKeyword();
        searchFormStore = new SearchFormStore(getApplicationContext());
        if (searchFormStore.isFormVisible()) {
            showForm();
        } else {
            hideForm();
        }

        View actionBarView = getLayoutInflater().inflate(R.layout.common_action_bar_circle_search_option, null);
        EventBlockSelectorView blockSelectorView = (EventBlockSelectorView) actionBarView
                .findViewById(R.id.common_action_bar_circle_search_option);
        blockSelectorView.setBlockList(EventBlockTable.getAll());
        blockSelectorView.addOnItemSelectedListener(new OnItemSelectedEventListener<Block>() {
            @Override
            public void onItemSelected(Block item) {
                searchOptionBuilder.setBlock(item);
                setSearchOption(searchOptionBuilder.build());
            }
        });
        blockSelectorView.setSelection(searchOptionBuilder.build().getBlock());
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(actionBarView);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ContentReloader reloader = (ContentReloader) getFragmentManager()
                        .findFragmentById(R.id.common_activity_circle_search_circles_container);
                if (reloader != null) {
                    reloader.reload();
                }
            }
        };
        registerReceiver(broadcastReceiver, BroadcastEvent.createIntentFilter());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search)
                .setActionProvider(new ActionProvider(getApplicationContext(), new ActionProvider.OnClickListener() {
                    @Override
                    public void onClick() {
                        showForm();
                    }
                }));
        inflater.inflate(R.menu.clear, menu);
        MenuItem hiddenItem = menu.findItem(R.id.menu_cancel)
                .setActionProvider(new ActionProvider(getApplicationContext(), new ActionProvider.OnClickListener() {
                    @Override
                    public void onClick() {
                        hideForm();
                    }
                }));
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

    @Override
    public void onDestroy() {
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
        super.onDestroy();
    }

    private void setSearchOption(CircleSearchOption searchOption) {
        Fragment fragment = getFragmentManager().findFragmentById(R.id.common_activity_circle_search_circles_container);
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

}
