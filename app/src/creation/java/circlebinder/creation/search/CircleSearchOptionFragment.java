package circlebinder.creation.search;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import net.ichigotake.common.os.BundleMerger;
import net.ichigotake.common.view.ActionProvider;
import net.ichigotake.common.view.inputmethod.SoftInput;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import circlebinder.common.checklist.BlockSelectorContainer;
import circlebinder.common.event.AllBlock;
import circlebinder.common.event.Block;
import circlebinder.common.event.BlockBuilder;
import circlebinder.common.search.CircleSearchOption;
import circlebinder.common.search.CircleSearchOptionBuilder;
import circlebinder.common.search.OnCircleSearchOptionListener;
import circlebinder.creation.app.BaseFragment;
import circlebinder.R;
import circlebinder.creation.event.BlockTable;

public final class CircleSearchOptionFragment extends BaseFragment {

    private static final String KEY_SEARCH_OPTION_BUILDER = "search_option_builder";

    private CircleSearchOptionBuilder searchOptionBuilder;
    private TextView searchOptionKeywordView;
    private EditText searchTextView;
    private View searchTextContainer;
    private SearchFormStore searchFormStore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        searchOptionBuilder = BundleMerger.merge(getArguments(), savedInstanceState)
                .getParcelable(KEY_SEARCH_OPTION_BUILDER);
        searchFormStore = new SearchFormStore(getActivity());
        if (searchOptionBuilder == null) {
            searchOptionBuilder = new CircleSearchOptionBuilder()
                    .setKeyword(searchFormStore.getKeyword());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search, menu);
        MenuItem showSearchItem = menu.findItem(R.id.menu_search);
        showSearchItem.setActionProvider(new ActionProvider(getActivity(), this::showForm));
        inflater.inflate(R.menu.clear, menu);
        MenuItem hideSearchItem = menu.findItem(R.id.menu_cancel);
        hideSearchItem.setActionProvider(new ActionProvider(getActivity(), this::hideForm));
        if (searchFormStore.isFormVisible()) {
            showSearchItem.setVisible(false);
            hideSearchItem.setVisible(true);
        } else {
            showSearchItem.setVisible(true);
            hideSearchItem.setVisible(false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_circle_search_option, parent, false);
        searchTextContainer = view;

        View actionBarView = inflater.inflate(R.layout.circle_search_option, null);
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(actionBarView);
        searchOptionKeywordView = (TextView) actionBarView.findViewById(R.id.circle_search_option_keyword);
        searchOptionKeywordView.setText(searchOptionBuilder.build().getKeyword());
        List<Block> blocks = new CopyOnWriteArrayList<>();
        blocks.add(new BlockBuilder().setName("全").setId(-1).build());
        blocks.addAll(BlockTable.get());
        BlockSelectorContainer selectorContainer = new BlockSelectorContainer(
                (Spinner)actionBarView.findViewById(R.id.circle_search_option_block_selector),
                blocks
        );
        selectorContainer.setSelection(new AllBlock(getActivity()));
        selectorContainer.addOnItemSelectedListener(item -> {
            searchOptionBuilder.setBlock(item);
            reload(searchOptionBuilder.build());
        });
        selectorContainer.setSelection(searchOptionBuilder.build().getBlock());

        searchTextView = (EditText)view.findViewById(
                R.id.fragment_circle_search_option_keyword
        );
        searchTextView.setText(searchOptionBuilder.build().getKeyword());
        searchTextView.requestFocus(searchOptionBuilder.build().getKeyword().length());
        searchTextView.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                onEditTextFocused();
            } else {
                onEditTextUnFocused();
            }
        });
        searchTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString();
                searchFormStore.setKeyword(keyword);
                reload(searchOptionBuilder.setKeyword(keyword).build());
            }
        });
        searchTextView.setOnEditorActionListener((v, actionId, event) -> {
            SoftInput.hide(v);
            v.clearFocus();
            return false;
        });
        if (searchFormStore.isFormVisible()) {
            showForm();
        } else {
            hideForm();
        }
        return view;
    }

    private void showForm() {
        searchFormStore.setFormVisible(true);
        reload(searchOptionBuilder.build());
    }

    private void hideForm() {
        searchFormStore.setFormVisible(false);
        reload(searchOptionBuilder.setKeyword("").build());
    }

    private void onEditTextFocused() {
        searchTextView.requestFocus();
    }

    private void onEditTextUnFocused() {
        searchTextView.clearFocus();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_SEARCH_OPTION_BUILDER, searchOptionBuilder);
    }

    private void reload(CircleSearchOption searchOption) {
        Activity activity = getActivity();
        if (activity != null) {
            ((OnCircleSearchOptionListener) activity).setSearchOption(searchOption);
        }
        Log.d("welcome", "eyword: " + searchOption.getKeyword());
        searchOptionKeywordView.setText(searchOption.getKeyword());
        Log.d("welcome", "editText: " + searchOptionKeywordView.getText());
        searchFormStore.setKeyword(searchOption.getKeyword());
        getActivity().invalidateOptionsMenu();
        if (searchFormStore.isFormVisible()) {
            searchTextContainer.setVisibility(View.VISIBLE);
        } else {
            searchTextContainer.setVisibility(View.GONE);
        }
    }

}
