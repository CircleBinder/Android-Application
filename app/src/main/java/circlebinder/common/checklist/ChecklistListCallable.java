package circlebinder.common.checklist;

import android.database.Cursor;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;

import circlebinder.common.event.Circle;
import circlebinder.common.search.CircleCursorConverter;
import circlebinder.common.search.CircleSearchOptionBuilder;
import circlebinder.common.table.EventCircleTable;

final class ChecklistListCallable implements Callable<List<Checklist>> {

    @Override
    public List<Checklist> call() throws Exception {
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
            if (cursor.getCount() > 0) {
                circleList.add(converter.create(cursor));
            }
            cursor.close();
            checklistList.add(new Checklist(circleList, checklistColor));
        }

        return new CopyOnWriteArrayList<>(checklistList);
    }
}
