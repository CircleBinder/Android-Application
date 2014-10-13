package circlebinder.common.checklist;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import net.ichigotake.common.app.ActivityTripper;
import net.ichigotake.common.app.OnClickToTrip;
import net.ichigotake.common.content.ContentReloader;
import net.ichigotake.common.widget.OnItemClickEventListener;
import net.ichigotake.common.widget.OnItemClickListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import circlebinder.common.event.Circle;
import circlebinder.common.search.CircleSearchOptionBuilder;
import circlebinder.common.app.BaseFragment;
import circlebinder.R;
import circlebinder.common.app.phone.ChecklistActivity;
import circlebinder.common.app.phone.CircleSearchActivity;
import circlebinder.common.table.EventCircleTable;
import circlebinder.common.search.CircleCursorConverter;

/**
 * アプリを起動した際の最初に表示する画面
 *
 * TODO: ListFragment を継承する
 */
public final class ChecklistListFragment extends BaseFragment implements ContentReloader {

    private ChecklistAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.common_fragment_checklist_list, parent, false);
        View headerView = inflater.inflate(R.layout.creation_enjoy_creation_search_guidance, parent, false);
        headerView.setOnClickListener(OnClickToTrip.activityTrip(
                getActivity(), CircleSearchActivity.createIntent(getActivity())
        ));
        ListView checklistsView = (ListView) view.findViewById(R.id.common_fragment_checklist_list);
        adapter = new ChecklistAdapter(getActivity());
        adapter.addAll(getChecklist());
        checklistsView.addHeaderView(headerView);
        checklistsView.setAdapter(adapter);
        OnItemClickListener<Checklist> listener = new OnItemClickListener<>();
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
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        reload();
    }

    @Override
    public void reload() {
        adapter.clear();
        adapter.addAll(getChecklist());
    }

    private List<Checklist> getChecklist() {
        List<Checklist> checklistList = new CopyOnWriteArrayList<>();
        CircleCursorConverter converter = new CircleCursorConverter();

        for (ChecklistColor checklistColor : ChecklistColor.checklists()) {
            List<Circle> circleList = new CopyOnWriteArrayList<>();
            Cursor cursor = EventCircleTable.find(
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

        return new CopyOnWriteArrayList<>(checklistList);
    }

}
