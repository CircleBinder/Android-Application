package circlebinder.creation.search;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import net.ichigotake.common.os.BundleMerger;
import net.ichigotake.common.view.inputmethod.SoftInput;
import net.ichigotake.common.widget.OnItemSelectedEventListener;

import java.util.ArrayList;
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
    private EditText searchTextView;
    private TextView searchOptionLabelView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchOptionBuilder = BundleMerger.merge(getArguments(), savedInstanceState)
                .getParcelable(KEY_SEARCH_OPTION_BUILDER);
        if (searchOptionBuilder == null) {
            searchOptionBuilder = new CircleSearchOptionBuilder();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_circle_search_option, parent, false);

        List<Block> blocks = new CopyOnWriteArrayList<>();
        blocks.add(new BlockBuilder().setName("全").setId(-1).build());
        blocks.addAll(BlockTable.get());
        BlockSelectorContainer selectorContainer = new BlockSelectorContainer(
                (Spinner)view.findViewById(R.id.fragment_circle_search_option_block_selector),
                blocks
        );
        selectorContainer.setSelection(new AllBlock(getActivity()));
        selectorContainer.addOnItemSelectedListener(new OnItemSelectedEventListener<Block>() {
            @Override
            public void onItemSelected(Block item) {
                searchOptionBuilder.setBlock(item);
                reloadTargetFragment(searchOptionBuilder.build());
            }

            @Override
            public void onNothingSelected() {

            }
        });
        selectorContainer.setSelection(searchOptionBuilder.build().getBlock());

        searchTextView = (EditText)view.findViewById(
                R.id.fragment_circle_search_option_keyword
        );
        searchTextView.setText(searchOptionBuilder.build().getKeyword());
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
                reloadTargetFragment(searchOptionBuilder.setKeyword(s.toString()).build());
            }
        });
        searchTextView.setOnEditorActionListener((v, actionId, event) -> {
            SoftInput.hide(v);
            v.clearFocus();
            return false;
        });
        searchOptionLabelView = (TextView) view.findViewById(R.id.fragment_circle_search_option_label);
        final View searchOptionLabelContainer = view.findViewById(R.id.fragment_circle_search_option_label_container);
        final View searchOptionContainer = view.findViewById(R.id.fragment_circle_search_option_container);
        view.findViewById(R.id.fragment_circle_search_option_collapse).setOnClickListener(v -> {
            searchOptionLabelContainer.setVisibility(View.VISIBLE);
            searchOptionContainer.setVisibility(View.GONE);
        });
        searchOptionLabelContainer.setOnClickListener(v -> {
            searchOptionLabelContainer.setVisibility(View.GONE);
            searchOptionContainer.setVisibility(View.VISIBLE);
        });
        return view;
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

    private void reloadTargetFragment(CircleSearchOption searchOption) {
        Activity activity = getActivity();
        if (activity != null) {
            ((OnCircleSearchOptionListener) activity).setSearchOption(searchOption);
        }
        if (searchOptionLabelView != null) {
            List<String> labels = new ArrayList<>();
            labels.add(searchOption.getBlock().getName() + " ブロック");
            if (searchOption.hasKeyword()) {
                labels.add("キーワード: " + searchOption.getKeyword());
            }
            searchOptionLabelView.setText(TextUtils.join(",", labels));
        }
    }

}
