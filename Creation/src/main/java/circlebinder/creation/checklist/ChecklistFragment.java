package circlebinder.creation.checklist;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import net.ichigotake.common.app.FragmentFactory;
import net.ichigotake.common.os.BundleMerger;

import circlebinder.common.app.FragmentTripper;
import circlebinder.common.checklist.ChecklistColor;
import circlebinder.common.search.CircleOrder;
import circlebinder.common.search.CircleSearchOptionBuilder;
import circlebinder.creation.BaseFragment;
import circlebinder.creation.R;
import circlebinder.creation.app.phone.CircleDetailPagerActivity;
import circlebinder.creation.search.CircleSearchContainer;
import circlebinder.creation.search.CircleSearchViewHolder;

/**
 * チェックリスト
 */
public final class ChecklistFragment extends BaseFragment {

    public static FragmentFactory<ChecklistFragment> factory() {
        return factory(ChecklistColor.ALL);
    }

    public static FragmentFactory<ChecklistFragment> factory(ChecklistColor checklist) {
        return new ChecklistFragmentFactory(checklist);
    }

    public static FragmentTripper tripper(FragmentManager fragmentManager, ChecklistColor checklist) {
        return new FragmentTripper(fragmentManager, factory(checklist));
    }

    private static class ChecklistFragmentFactory implements FragmentFactory<ChecklistFragment> {

        final private ChecklistColor checklist;

        public ChecklistFragmentFactory(ChecklistColor checklist) {
            this.checklist = checklist;
        }

        @Override
        public ChecklistFragment create() {
            ChecklistFragment fragment = new ChecklistFragment();
            Bundle args = new Bundle();
            CircleSearchOptionBuilder searchOptionBuilder = new CircleSearchOptionBuilder()
                    .setChecklist(checklist);
            args.putParcelable(KEY_SEARCH_OPTION_BUILDER, searchOptionBuilder);
            fragment.setArguments(args);
            return fragment;
        }
    }

    private final static String KEY_SEARCH_OPTION_BUILDER = "search_option_builder";
    private CircleSearchContainer favoritesContainer;
    private CircleSearchOptionBuilder searchOptionBuilder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        searchOptionBuilder = BundleMerger.merge(getArguments(), savedInstanceState)
                .getParcelable(KEY_SEARCH_OPTION_BUILDER);
        if (searchOptionBuilder == null) {
            searchOptionBuilder = new CircleSearchOptionBuilder();
        }
        searchOptionBuilder.setOrder(CircleOrder.CHECKLIST);
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
        favoritesContainer = new CircleSearchContainer(getActivity(), viewHolder);
        favoritesContainer.getViewHolder().getCircles().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CircleDetailPagerActivity.tripper(getActivity(), searchOptionBuilder.build(), position);
            }
        });

        view.findViewById(R.id.circlebinder_fragment_circle_search_option).setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (searchOptionBuilder != null
                && getActivity() != null && getActivity().getActionBar() != null) {
            ChecklistColor checklistColor = searchOptionBuilder.build().getChecklist();
            getActivity().getActionBar().setTitle(
                    checklistColor != null ? checklistColor.getName() : ""
            );
        }
        if (favoritesContainer != null && searchOptionBuilder != null) {
            favoritesContainer.reload(searchOptionBuilder.build());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_SEARCH_OPTION_BUILDER, searchOptionBuilder);
    }

}
