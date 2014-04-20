package circlebinder.creation.system;

import android.content.Context;

import circlebinder.common.dashboard.SectionHeaderItemAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public final class ListItemAdapter extends SectionHeaderItemAdapter
        implements StickyListHeadersAdapter {

    public ListItemAdapter(Context context) {
        super(context);
    }

}
