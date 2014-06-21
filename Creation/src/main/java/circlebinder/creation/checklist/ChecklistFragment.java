package circlebinder.creation.checklist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import circlebinder.common.app.FragmentTripper;
import circlebinder.common.checklist.ChecklistColor;
import circlebinder.common.checklist.ChecklistSelectorContainer;
import circlebinder.common.search.CircleOrder;
import circlebinder.common.search.CircleSearchOptionBuilder;
import circlebinder.common.search.CircleSearchOptionContainer;
import circlebinder.common.search.PaneUpdateOnSearchActionListener;
import circlebinder.creation.BaseFragment;
import circlebinder.creation.R;
import circlebinder.creation.app.phone.CircleDetailPagerActivity;
import circlebinder.creation.search.CircleSearchContainer;
import circlebinder.creation.search.CircleSearchViewHolder;

/**
 * チェックリスト
 */
public final class ChecklistFragment extends BaseFragment implements Pane {

    public static FragmentFactory<ChecklistFragment> factory() {
        return factory(ChecklistColor.BLUE);
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
    private CircleSearchOptionContainer searchOptionContainer;
    private CircleSearchContainer favoritesContainer;
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
        CircleSearchViewHolder viewHolder = new CircleSearchViewHolder(
                (ListView)view.findViewById(R.id.fragment_circle_search_list),
                getActivity().getLayoutInflater().inflate(R.layout.circlebinder_fragment_checklist_empty, null)
        );
        ((ViewGroup)view.findViewById(R.id.circlebinder_fragment_circle_search_item_container))
                .addView(viewHolder.getEmptyView());
        favoritesContainer = new CircleSearchContainer(getActivity(), viewHolder);
        favoritesContainer.getViewHolder().getCircles().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CircleDetailPagerActivity.class);
                Bundle args = new Bundle();
                args.putParcelable(CircleDetailPagerActivity.EXTRA_KEY_SEARCH_OPTION, searchOptionBuilder.build());
                args.putInt(CircleDetailPagerActivity.EXTRA_KEY_POSITION, position);
                intent.putExtras(args);
                getActivity().startActivityFromFragment(
                        ChecklistFragment.this,
                        intent,
                        CircleDetailPagerActivity.REQUEST_CODE_CALLBACK
                );
            }
        });

        SearchView searchView = (SearchView) view.findViewById(R.id.circlebinder_fragment_circle_search_keyword);
        searchOptionContainer = new CircleSearchOptionContainer(
                searchView,
                new PaneUpdateOnSearchActionListener(this)
        );

        List<ChecklistColor> checklists = new CopyOnWriteArrayList<ChecklistColor>();
        checklists.add(ChecklistColor.ALL);
        Collections.addAll(checklists, ChecklistColor.checklists());
        ChecklistSelectorContainer selectorContainer = new ChecklistSelectorContainer(
                (Spinner) view.findViewById(R.id.circlebinder_fragment_circle_search_selector),
                checklists,
                ChecklistColor.ALL
        );
        selectorContainer.addOnItemSelectedListener(new OnItemSelectedEventListener<ChecklistColor>() {
            @Override
            public void onItemSelected(ChecklistColor item) {
                searchOptionBuilder.setKeyword(searchOptionContainer.getQuery());
                searchOptionBuilder.setChecklist(item);
                favoritesContainer.reload(searchOptionBuilder.build());
                tap();
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        tap();
    }

    @Override
    public void tap() {
        if (favoritesContainer != null) {
            if (searchOptionContainer != null) {
                favoritesContainer.reload(
                        searchOptionBuilder.setKeyword(searchOptionContainer.getQuery()).build()
                );
            } else {
                favoritesContainer.reload();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean doesPositionCallback = favoritesContainer != null
                && requestCode == CircleDetailPagerActivity.REQUEST_CODE_CALLBACK;
        if (doesPositionCallback
                && data != null && data.hasExtra(CircleDetailPagerActivity.EXTRA_KEY_POSITION)) {
            int callbackPosition = data.getIntExtra(CircleDetailPagerActivity.EXTRA_KEY_POSITION, -1);
            if (callbackPosition >= 0) {
                favoritesContainer.setPosition(callbackPosition);
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
