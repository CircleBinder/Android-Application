package circlebinder.creation.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import net.ichigotake.common.app.FragmentFactory;
import net.ichigotake.common.os.RestoreBundle;

import circlebinder.common.search.CircleSearchOption;
import circlebinder.common.search.CircleSearchOptionBuilder;
import circlebinder.creation.BaseFragment;
import circlebinder.creation.R;
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

        ImageView searchOptionView = (ImageView) view.findViewById(
                R.id.circlebinder_fragment_circle_search_option
        );
        searchOptionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CircleSearchOptionFragment.tripper(
                        getFragmentManager(),
                        CircleSearchFragment.this,
                        searchOptionBuilder.build()
                ).trip();
            }
        });
        searchOptionView.setImageResource(R.drawable.ic_search_option);
    }

    @Override
    public void onResume() {
        super.onResume();
        reload();
    }

    public void reload() {
        if (getActivity() != null && getActivity().getActionBar() != null) {
            getActivity().getActionBar().setTitle(R.string.circlebinder_search_circle);
        }
        if (searchContainer != null) {
            searchContainer.reload(searchOptionBuilder.build());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_SEARCH_OPTION_BUILDER, searchOptionBuilder);
    }

    @Override
    public void setSearchOption(CircleSearchOption searchOption) {
        searchOptionBuilder.set(searchOption);
        reload();
    }

}
