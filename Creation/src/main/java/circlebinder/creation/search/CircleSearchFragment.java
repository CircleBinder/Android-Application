package circlebinder.creation.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import net.ichigotake.common.app.FragmentFactory;
import net.ichigotake.common.app.Pane;
import net.ichigotake.common.os.RestoreBundle;
import net.ichigotake.common.widget.OnItemSelectedEventListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import circlebinder.common.checklist.BlockSelectorContainer;
import circlebinder.common.event.AllBlock;
import circlebinder.common.event.Block;
import circlebinder.common.event.BlockBuilder;
import circlebinder.common.search.CircleSearchOptionBuilder;
import circlebinder.common.search.CircleSearchOptionContainer;
import circlebinder.common.search.PaneUpdateOnSearchActionListener;
import circlebinder.creation.BaseFragment;
import circlebinder.creation.R;
import circlebinder.creation.app.phone.CircleDetailPagerActivity;
import circlebinder.creation.event.BlockTable;

/**
 * サークルの検索をする
 */
public final class CircleSearchFragment extends BaseFragment implements Pane {

    public static FragmentFactory<CircleSearchFragment> factory() {
        return new CircleSearchFragmentFactory();
    }

    private static class CircleSearchFragmentFactory implements FragmentFactory<CircleSearchFragment> {
        @Override
        public CircleSearchFragment create() {
            return new CircleSearchFragment();
        }
    }

    private final String KEY_SEARCH_OPTION_BUILDER = "search_option_builder";
    private CircleSearchContainer searchContainer;
    private CircleSearchOptionContainer searchOptionContainer;
    private CircleSearchOptionBuilder searchOptionBuilder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        searchOptionBuilder = new RestoreBundle(this, savedInstanceState)
                .getParcelable(KEY_SEARCH_OPTION_BUILDER);
        if (searchOptionBuilder == null) {
            searchOptionBuilder = new CircleSearchOptionBuilder();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.circlebinder_fragment_circle_search, parent, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewGroup view = (ViewGroup) getView();
        CircleSearchViewHolder viewHolder = new CircleSearchViewHolder(
                (ListView)view.findViewById(R.id.fragment_circle_search_list),
                getActivity().getLayoutInflater().inflate(R.layout.circlebinder_fragment_checklist_empty, null)
        );
        searchContainer = new CircleSearchContainer(getActivity(), viewHolder);
        searchContainer.getViewHolder().getCircles().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CircleDetailPagerActivity.class);
                Bundle args = new Bundle();
                args.putParcelable(CircleDetailPagerActivity.EXTRA_KEY_SEARCH_OPTION, searchOptionBuilder.build());
                args.putInt(CircleDetailPagerActivity.EXTRA_KEY_POSITION, position);
                intent.putExtras(args);
                getActivity().startActivityFromFragment(
                        CircleSearchFragment.this,
                        intent,
                        CircleDetailPagerActivity.REQUEST_CODE_CALLBACK
                );
            }
        });

        List<Block> blocks = new CopyOnWriteArrayList<Block>();
        blocks.add(new BlockBuilder().setName("全").setId(-1).build());
        blocks.addAll(BlockTable.get());
        BlockSelectorContainer selectorContainer = new BlockSelectorContainer(
                (Spinner)view.findViewById(R.id.circlebinder_fragment_circle_search_selector),
                blocks
        );
        selectorContainer.setSelection(new AllBlock(getActivity()));
        selectorContainer.addOnItemSelectedListener(new OnItemSelectedEventListener<Block>() {
            @Override
            public void onItemSelected(Block item) {
                searchOptionBuilder.setKeyword(searchOptionContainer.getQuery());
                searchOptionBuilder.setBlock(item);
                searchContainer.reload(searchOptionBuilder.build());
                tap();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        searchOptionContainer = new CircleSearchOptionContainer(
                (SearchView)view.findViewById(R.id.circlebinder_fragment_circle_search_keyword),
                new PaneUpdateOnSearchActionListener(this)
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        tap();
    }

    @Override
    public void tap() {
        if (searchContainer != null) {
            if (searchOptionContainer != null) {
                searchContainer.reload(
                        searchOptionBuilder.setKeyword(searchOptionContainer.getQuery()).build()
                );
            } else {
                searchContainer.reload();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean doesPositionCallback = searchContainer != null
                && requestCode == CircleDetailPagerActivity.REQUEST_CODE_CALLBACK;
        if (doesPositionCallback
                && data != null && data.hasExtra(CircleDetailPagerActivity.EXTRA_KEY_POSITION)) {
            int callbackPosition = data.getIntExtra(CircleDetailPagerActivity.EXTRA_KEY_POSITION, -1);
            if (callbackPosition >= 0) {
                searchContainer.setPosition(callbackPosition);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState == null) {
            outState = new Bundle();
        }
        outState.putParcelable(KEY_SEARCH_OPTION_BUILDER, searchOptionBuilder);
        super.onSaveInstanceState(outState);
    }

}
