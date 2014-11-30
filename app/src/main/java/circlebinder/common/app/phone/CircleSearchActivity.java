package circlebinder.common.app.phone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import net.ichigotake.common.app.ActivityNavigation;
import net.ichigotake.common.util.ActivityViewFinder;
import net.ichigotake.common.util.Finders;
import net.ichigotake.common.view.ActionProvider;
import net.ichigotake.common.view.MenuPresenter;
import net.ichigotake.common.widget.OnItemSelectedEventListener;

import circlebinder.common.app.BroadcastEvent;
import circlebinder.common.checklist.EventBlockSelectorView;
import circlebinder.common.event.Block;
import circlebinder.common.search.CircleSearchOption;
import circlebinder.common.search.CircleSearchOptionBuilder;
import circlebinder.common.search.CircleSearchView;
import circlebinder.common.search.InputKeywordView;
import circlebinder.common.app.BaseActivity;
import circlebinder.R;
import circlebinder.common.search.OnInputTextListener;
import circlebinder.common.search.SearchFormStore;
import circlebinder.common.table.EventBlockTable;

public final class CircleSearchActivity extends BaseActivity {

    public static Intent createIntent(Context context) {
        return new Intent(context, CircleSearchActivity.class);
    }

    private CircleSearchOptionBuilder searchOptionBuilder = new CircleSearchOptionBuilder();
    private CircleSearchView circlesView;
    private SearchFormStore searchFormStore;
    private InputKeywordView inputKeywordView;
    private BroadcastReceiver broadcastReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_circle_search);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        ActivityViewFinder finder = Finders.from(this);
        circlesView = finder.findOrNull(R.id.common_activity_circle_search_circles);

        inputKeywordView = finder.findOrNull(R.id.common_activity_circle_search_option_keyword);
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
        EventBlockSelectorView blockSelectorView = Finders.from(actionBarView)
                .findOrNull(R.id.common_action_bar_circle_search_option);
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
                if (circlesView != null) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            circlesView.reload();
                        }
                    });
                }
            }
        };
        registerReceiver(broadcastReceiver, BroadcastEvent.createIntentFilter());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuPresenter presenter = new MenuPresenter(menu, getMenuInflater());
        MenuItem searchItem = presenter.inflate(R.menu.search, R.id.menu_search);
        presenter.setActionProvider(
                searchItem,
                new ActionProvider(getApplicationContext(), new ActionProvider.OnClickListener() {
                    @Override
                    public void onClick() {
                        showForm();
                    }
                }));
        MenuItem hiddenItem = presenter.inflate(R.menu.clear, R.id.menu_cancel);
        presenter.setActionProvider(hiddenItem, new ActionProvider(getApplicationContext(), new ActionProvider.OnClickListener() {
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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return ActivityNavigation.back(this, menuItem)
                || super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onDestroy() {
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
        super.onDestroy();
    }

    private void setSearchOption(CircleSearchOption searchOption) {
        circlesView.setFilter(searchOption);
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
