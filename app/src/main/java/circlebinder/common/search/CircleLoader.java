package circlebinder.common.search;

import android.content.Context;
import android.database.Cursor;

import net.ichigotake.common.content.AsyncTaskLoader;

import circlebinder.common.search.CircleSearchOption;
import circlebinder.common.table.EventCircleTable;

public final class CircleLoader extends AsyncTaskLoader<Cursor> {

    private final CircleSearchOption searchOption;

    public CircleLoader(Context context, CircleSearchOption searchOption) {
        super(context);
        this.searchOption = searchOption;
    }

    @Override
    public Cursor loadInBackground() {
        return EventCircleTable.find(searchOption);
    }

}
