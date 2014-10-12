package circlebinder.common.search;

import android.database.Cursor;
import android.widget.FilterQueryProvider;

import circlebinder.creation.event.EventCircleTable;

public final class CircleQueryProvider implements FilterQueryProvider {

    private final CircleSearchOption circleSearchOption;

    public CircleQueryProvider(CircleSearchOption circleSearchOption) {
        this.circleSearchOption = circleSearchOption;
    }

    @Override
    public Cursor runQuery(CharSequence constraint) {
        return EventCircleTable.find(circleSearchOption);
    }

}