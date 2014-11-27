package circlebinder.common.checklist;

import android.view.View;
import android.widget.TextView;

import net.ichigotake.common.util.Finders;
import net.ichigotake.common.util.ViewFinder;

import circlebinder.R;

public final class ChecklistSelectorViewHolder {

    private final TextView name;
    private final ChecklistSelectorView icon;

    public ChecklistSelectorViewHolder(View container) {
        ViewFinder finder = Finders.from(container);
        name = finder.find(R.id.common_checklist_selector_dropdown_item_name);
        icon = finder.find(R.id.common_checklist_selector_dropdown_item_icon);
    }

    public TextView getName() {
        return name;
    }

    public ChecklistSelectorView getIcon() {
        return icon;
    }
}
