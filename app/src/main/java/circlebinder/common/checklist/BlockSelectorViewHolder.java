package circlebinder.common.checklist;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.ichigotake.common.util.Finders;
import net.ichigotake.common.util.ViewFinder;

import circlebinder.R;

public final class BlockSelectorViewHolder {

    private final TextView name;
    private final ImageView icon;

    public BlockSelectorViewHolder(View container) {
        ViewFinder finder = Finders.from(container);
        name = finder.find(R.id.common_spinner_dropdown_item_name);
        icon = finder.find(R.id.common_spinner_dropdown_item_icon);
    }

    public TextView getName() {
        return name;
    }

    public ImageView getIcon() {
        return icon;
    }
}
