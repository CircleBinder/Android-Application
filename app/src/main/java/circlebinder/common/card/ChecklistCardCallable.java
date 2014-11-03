package circlebinder.common.card;

import android.database.Cursor;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;

import circlebinder.common.checklist.ChecklistColor;
import circlebinder.common.event.Circle;
import circlebinder.common.search.CircleCursorConverter;
import circlebinder.common.search.CircleSearchOption;
import circlebinder.common.search.CircleSearchOptionBuilder;
import circlebinder.common.table.EventCircleTable;

final class ChecklistCardCallable implements Callable<List<HomeCard>> {

    @Override
    public List<HomeCard> call() throws Exception {
        List<HomeCard> cardList = new CopyOnWriteArrayList<>();
        CircleCursorConverter converter = new CircleCursorConverter();
        for (ChecklistColor checklistColor : ChecklistColor.checklists()) {
            CircleSearchOption option = new CircleSearchOptionBuilder()
                    .setChecklist(checklistColor)
                    .build();
            Cursor cursor = EventCircleTable.find(option);
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                Circle circle = converter.create(cursor);
                cardList.add(new ChecklistCard(circle));
            }
            cursor.close();
        }

        return cardList;
    }
}
