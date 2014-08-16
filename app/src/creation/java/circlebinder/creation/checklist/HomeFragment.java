package circlebinder.creation.checklist;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import net.ichigotake.common.app.ActivityTripper;
import net.ichigotake.common.app.OnClickToTrip;
import net.ichigotake.common.widget.OnItemClickEventListener;
import net.ichigotake.common.widget.OnItemClickListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import circlebinder.common.checklist.ChecklistColor;
import circlebinder.common.event.Circle;
import circlebinder.common.search.CircleSearchOptionBuilder;
import circlebinder.creation.app.BaseFragment;
import circlebinder.R;
import circlebinder.creation.app.phone.ChecklistActivity;
import circlebinder.creation.app.phone.CircleSearchActivity;
import circlebinder.creation.event.CircleTable;
import circlebinder.creation.search.CircleCursorConverter;

/**
 * アプリを起動した際の最初に表示する画面
 */
public final class HomeFragment extends BaseFragment {

    private GridView checklistsView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checklist_list, parent, false);
        View emptyView = view.findViewById(R.id.fragment_checklist_empty);
        emptyView.setOnClickListener(
                new OnClickToTrip(new ActivityTripper(
                        getActivity(),
                        CircleSearchActivity.createIntent(getActivity())
                ))
        );
        checklistsView = (GridView) view.findViewById(R.id.fragment_checklist_list);
        checklistsView.setEmptyView(emptyView);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ChecklistAdapter adapter = new ChecklistAdapter(getActivity());
        adapter.addAll(getChecklist());
        checklistsView.setAdapter(adapter);
        OnItemClickListener<Checklist> listener = new OnItemClickListener<Checklist>();
        listener.addOnItemClickEventListener(new OnItemClickEventListener<Checklist>() {
            @Override
            public void onItemClick(Checklist item) {
                new ActivityTripper(
                        getActivity(),
                        ChecklistActivity.createIntent(getActivity(), item.getChecklistColor())
                ).trip();
            }
        });
        checklistsView.setOnItemClickListener(listener);
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
