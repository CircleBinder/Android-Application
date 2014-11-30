package circlebinder.common.changelog;

import android.view.View;
import android.widget.TextView;

import net.ichigotake.common.util.Finders;
import net.ichigotake.common.util.ViewFinder;

import circlebinder.R;

public final class ChangeLogFeedViewHolder {

    private final TextView title;

    public ChangeLogFeedViewHolder(View container) {
        ViewFinder finder = Finders.from(container);
        title = finder.findOrNull(R.id.common_list_item_label);
    }

    public TextView getLabel() {
        return title;
    }

}
