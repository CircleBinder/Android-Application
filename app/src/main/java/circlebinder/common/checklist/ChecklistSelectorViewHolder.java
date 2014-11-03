package circlebinder.common.checklist;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import circlebinder.R;

public final class ChecklistSelectorViewHolder {

    private final TextView name;
    private final ChecklistSelectorView icon;

    public ChecklistSelectorViewHolder(View convertView) {
        name = (TextView)convertView.findViewById(R.id.common_checklist_selector_dropdown_item_name);
        icon = (ChecklistSelectorView)convertView.findViewById(R.id.common_checklist_selector_dropdown_item_icon);
    }

    public TextView getName() {
        return name;
    }

    public ChecklistSelectorView getIcon() {
        return icon;
    }
}
