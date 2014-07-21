package circlebinder.creation.checklist;

import android.app.FragmentManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import net.ichigotake.common.app.FragmentFactory;
import net.ichigotake.common.app.OnClickToTrip;
import net.ichigotake.common.widget.OnItemClickEventListener;
import net.ichigotake.common.widget.OnItemClickListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import circlebinder.common.app.FragmentTripper;
import circlebinder.common.checklist.ChecklistColor;
import circlebinder.common.event.Circle;
import circlebinder.common.search.CircleSearchOptionBuilder;
import circlebinder.creation.BaseFragment;
import circlebinder.R;
import circlebinder.creation.app.phone.CircleSearchActivity;
import circlebinder.creation.event.CircleTable;
import circlebinder.creation.search.CircleCursorConverter;

/**
 * アプリを起動した際の最初に表示する画面
 */
public final class HomeFragment extends BaseFragment {

    public static FragmentFactory<HomeFragment> factory() {
        return new ChecklistFragmentFactory();
    }

    public static FragmentTripper tripper(FragmentManager fragmentManager) {
        return new FragmentTripper(fragmentManager, factory());
    }

    private static class ChecklistFragmentFactory implements FragmentFactory<HomeFragment> {

        @Override
        public HomeFragment create() {
            return new HomeFragment();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.circlebinder_fragment_checklist, parent, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        ChecklistAdapter adapter = new ChecklistAdapter(getActivity());
        adapter.addAll(getChecklist());
        GridView checklistsView = (GridView) getView().findViewById(R.id.circlebinder_fragment_checklist_list);
        checklistsView.setAdapter(adapter);
        OnItemClickListener<Checklist> listener = new OnItemClickListener<Checklist>();
        listener.addOnItemClickEventListener(new OnItemClickEventListener<Checklist>() {
            @Override
            public void onItemClick(Checklist item) {
                ChecklistFragment
                        .tripper(getFragmentManager(), item.getChecklistColor())
                        .setLayoutId(R.id.activity_fragment_content)
                        .trip();
            }
        });
        checklistsView.setOnItemClickListener(listener);

        getView().findViewById(R.id.fragment_checklist_header_label).setOnClickListener(
                new OnClickToTrip(CircleSearchActivity.tripper(getActivity()))
        );
    }

    private List<Checklist> getChecklist() {
        List<Checklist> checklistList = new CopyOnWriteArrayList<Checklist>();
        CircleCursorConverter converter = new CircleCursorConverter();

        for (ChecklistColor checklistColor : ChecklistColor.checklists()) {
            List<Circle> circleList = new CopyOnWriteArrayList<Circle>();
            Cursor cursor = CircleTable.get(
                    new CircleSearchOptionBuilder()
                            .setChecklist(checklistColor)
                            .build()
            );
            cursor.moveToFirst();
            for (int i=0; i<4; i++) {
                if (cursor.move(i)) {
                    circleList.add(converter.create(cursor));
                } else {
                    break;
                }
            }
            if (!circleList.isEmpty()) {
                checklistList.add(new Checklist(circleList, checklistColor));
            }
        }

        return new CopyOnWriteArrayList<Checklist>(checklistList);
    }

}
