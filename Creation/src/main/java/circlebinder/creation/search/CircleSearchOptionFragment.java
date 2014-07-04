package circlebinder.creation.search;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import net.ichigotake.common.app.FragmentFactory;
import net.ichigotake.common.os.RestoreBundle;
import net.ichigotake.common.view.inputmethod.SoftInput;
import net.ichigotake.common.widget.OnItemSelectedEventListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import circlebinder.common.app.FragmentTripper;
import circlebinder.common.checklist.BlockSelectorContainer;
import circlebinder.common.event.AllBlock;
import circlebinder.common.event.Block;
import circlebinder.common.event.BlockBuilder;
import circlebinder.common.search.CircleSearchOption;
import circlebinder.common.search.CircleSearchOptionBuilder;
import circlebinder.creation.BaseFragment;
import circlebinder.creation.R;
import circlebinder.creation.event.BlockTable;

public final class CircleSearchOptionFragment extends BaseFragment {

    private static final String KEY_FRAGMENT_TAG = CircleSearchOptionFragment.class.getName();
    private static final String KEY_SEARCH_OPTION_BUILDER = "search_option_builder";

    @SuppressWarnings("unchecked")
    public static <T extends Fragment & OnCircleSearchOptionListener> FragmentTripper tripper(
            FragmentManager fragmentManager,
            T callback,
            CircleSearchOption searchOption
    ) {
        return new FragmentTripper(
                fragmentManager,
                new CircleSearchOptionFragmentFactory(callback, searchOption)
        ).setTag(KEY_FRAGMENT_TAG);
    }

    private static class CircleSearchOptionFragmentFactory<T extends Fragment & OnCircleSearchOptionListener>
            implements FragmentFactory<CircleSearchOptionFragment> {

        private final T callback;
        private final CircleSearchOption searchOption;

        public CircleSearchOptionFragmentFactory(T callback, CircleSearchOption searchOption) {
            this.callback = callback;
            this.searchOption = searchOption;
        }

        @Override
        public CircleSearchOptionFragment create() {
            CircleSearchOptionFragment fragment = new CircleSearchOptionFragment();
            Bundle args = new Bundle();
            args.putParcelable(KEY_SEARCH_OPTION_BUILDER, new CircleSearchOptionBuilder(searchOption));
            fragment.setArguments(args);
            fragment.setTargetFragment(callback, 0);
            return fragment;
        }
    }

    private CircleSearchOptionBuilder searchOptionBuilder;
    private EditText searchTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchOptionBuilder = new RestoreBundle(this, savedInstanceState)
                .getParcelable(KEY_SEARCH_OPTION_BUILDER);
        if (searchOptionBuilder == null) {
            searchOptionBuilder = new CircleSearchOptionBuilder();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.circlebinder_fragment_circle_search_option, parent, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();

        List<Block> blocks = new CopyOnWriteArrayList<Block>();
        blocks.add(new BlockBuilder().setName("全").setId(-1).build());
        blocks.addAll(BlockTable.get());
        BlockSelectorContainer selectorContainer = new BlockSelectorContainer(
                (Spinner)view.findViewById(R.id.circlebinder_fragment_circle_search_option_selector),
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
                R.id.circlebinder_fragment_circle_search_keyword
        );
        searchTextView.setText(searchOptionBuilder.build().getKeyword());
        searchTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    onEditTextFocused();
                } else {
                    onEditTextUnFocused();
                }
            }
        });
        searchTextView.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                reloadTargetFragment(searchOptionBuilder.setKeyword(s.toString()).build());
            }
        });
        searchTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                SoftInput.hide(v);
                v.clearFocus();
                return false;
            }
        });

        view.findViewById(R.id.circlebinder_fragment_circle_search_option_cancel)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });
    }

    private void dismiss() {
        reloadTargetFragment(searchOptionBuilder.build());
        SoftInput.hide(getView());
        getFragmentManager().beginTransaction()
                .remove(this)
                .commit();
        getFragmentManager().popBackStack();
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
        Fragment targetFragment = getTargetFragment();
        ((OnCircleSearchOptionListener)targetFragment).setSearchOption(searchOption);
    }

}
