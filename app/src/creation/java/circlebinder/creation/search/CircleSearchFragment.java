package circlebinder.creation.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import net.ichigotake.common.app.FragmentFactory;
import net.ichigotake.common.os.BundleMerger;
import net.ichigotake.common.widget.OnItemClickEventListener;

import circlebinder.common.checklist.ChecklistColor;
import circlebinder.common.checklist.ChecklistPopupSelector;
import circlebinder.common.circle.CircleAdapter;
import circlebinder.common.circle.CircleViewHolder;
import circlebinder.common.circle.OnCircleItemClickListener;
import circlebinder.common.event.BlockBuilder;
import circlebinder.common.event.Circle;
import circlebinder.common.search.CircleSearchOption;
import circlebinder.common.search.CircleSearchOptionBuilder;
import circlebinder.creation.BaseFragment;
import circlebinder.R;
import circlebinder.creation.app.phone.CircleDetailPagerActivity;
import circlebinder.creation.event.CircleTable;

/**
 * サークルの検索をする
 */
public final class CircleSearchFragment extends BaseFragment implements OnCircleSearchOptionListener {

    public static FragmentFactory<CircleSearchFragment> factory(CircleSearchOption searchOption) {
        return new CircleSearchFragmentFactory(searchOption);
    }

    private static class CircleSearchFragmentFactory implements FragmentFactory<CircleSearchFragment> {

        private final CircleSearchOption searchOption;

        public CircleSearchFragmentFactory(CircleSearchOption searchOption) {
            this.searchOption = searchOption;
        }

        @Override
        public CircleSearchFragment create() {
            CircleSearchFragment fragment = new CircleSearchFragment();
            Bundle args = new Bundle();
            args.putParcelable(KEY_SEARCH_OPTION_BUILDER, new CircleSearchOptionBuilder(searchOption));
            fragment.setArguments(args);
            return fragment;
        }
    }

    private static final String KEY_SEARCH_OPTION_BUILDER = "search_option_builder";
    private CircleSearchOptionBuilder searchOptionBuilder;
    private CircleAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchOptionBuilder = BundleMerger.merge(getArguments(), savedInstanceState)
                .getParcelable(KEY_SEARCH_OPTION_BUILDER);
        if (searchOptionBuilder == null) {
            searchOptionBuilder = new CircleSearchOptionBuilder()
                    .setBlock(new BlockBuilder().setName("全").setId(-1).build());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.fragment_circle_search, parent, false);
        View emptyView = getActivity().getLayoutInflater()
                .inflate(R.layout.circlebinder_fragment_checklist_empty, view, false);
        CircleSearchViewHolder viewHolder = new CircleSearchViewHolder(
                (ListView)view.findViewById(R.id.fragment_circle_search_list)
        );
        viewHolder.getCircles().setEmptyView(emptyView);
        viewHolder.getCircles().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CircleDetailPagerActivity.tripper(getActivity(), searchOptionBuilder.build(), position)
                        .trip();
            }
        });
        adapter = new CircleAdapter(
                getActivity(),
                null,
                new CircleCursorConverter(),
                new OnCircleItemClickListener() {
                    @Override
                    public void onSpaceClick(final CircleViewHolder viewHolder, int position, final Circle item) {
                        final ChecklistPopupSelector selector = new ChecklistPopupSelector(
                                getActivity(), viewHolder.getSpaceContainer()
                        );
                        selector.setOnItemClickListener(new OnItemClickEventListener<ChecklistColor>() {
                            @Override
                            public void onItemClick(ChecklistColor checklistColor) {
                                viewHolder.getSpace().setCompoundDrawablesWithIntrinsicBounds(
                                        0,
                                        checklistColor.getDrawableResource(),
                                        0,
                                        0
                                );
                                CircleTable.setChecklist(item, checklistColor);
                                adapter.reload();
                                selector.dismiss();
                                
                            }
                        });
                        selector.show();
                    }
                }
        );
        viewHolder.getCircles().setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setSearchOption(searchOptionBuilder.build());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_SEARCH_OPTION_BUILDER, searchOptionBuilder);
    }

    @Override
    public void setSearchOption(CircleSearchOption searchOption) {
        searchOptionBuilder.set(searchOption);
        adapter.setFilterQueryProvider(new CircleQueryProvider(searchOption));
        adapter.getFilter().filter("");
    }

}
