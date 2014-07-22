package circlebinder.creation.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import net.ichigotake.common.app.FragmentFactory;
import net.ichigotake.common.os.BundleMerger;

import circlebinder.common.event.BlockBuilder;
import circlebinder.common.search.CircleSearchOption;
import circlebinder.common.search.CircleSearchOptionBuilder;
import circlebinder.creation.BaseFragment;
import circlebinder.R;
import circlebinder.creation.app.phone.CircleDetailPagerActivity;

/**
 * サークルの検索をする
 */
public final class CircleSearchFragment extends BaseFragment implements OnCircleSearchOptionListener {

    public static FragmentFactory<CircleSearchFragment> factory() {
        return new CircleSearchFragmentFactory();
    }

    private static class CircleSearchFragmentFactory implements FragmentFactory<CircleSearchFragment> {
        @Override
        public CircleSearchFragment create() {
            return new CircleSearchFragment();
        }
    }

    private static final String KEY_SEARCH_OPTION_BUILDER = "search_option_builder";
    private CircleSearchOptionBuilder searchOptionBuilder;
    private CircleSearchContainer searchContainer;

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
        searchContainer = new CircleSearchContainer(getActivity(), viewHolder);
        searchContainer.getViewHolder().getCircles().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CircleDetailPagerActivity.tripper(getActivity(), searchOptionBuilder.build(), position)
                        .trip();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null && getActivity().getActionBar() != null) {
            getActivity().getActionBar().setTitle(R.string.circlebinder_search_circle);
        }
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
        if (searchContainer != null) {
            searchContainer.reload(searchOptionBuilder.build());
        }
    }

}
