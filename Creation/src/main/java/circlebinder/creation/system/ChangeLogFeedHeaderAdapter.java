package circlebinder.creation.system;

import android.content.Context;

import circlebinder.common.changelog.ChangeLogFeedAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public final class ChangeLogFeedHeaderAdapter extends ChangeLogFeedAdapter
        implements StickyListHeadersAdapter {

    public ChangeLogFeedHeaderAdapter(Context context) {
        super(context);
    }
}
