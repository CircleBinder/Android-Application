package circlebinder.common.checklist;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import circlebinder.creation.R;

public final class SelectorViewHolder {

    private final TextView name;
    private final ImageView icon;

    public SelectorViewHolder(View convertView) {
        name = (TextView)convertView.findViewById(R.id.circlebinder_spinner_dropdown_item_name);
        icon = (ImageView)convertView.findViewById(R.id.circlebinder_spinner_dropdown_item_icon);
    }

    public TextView getName() {
        return name;
    }

    public ImageView getIcon() {
        return icon;
    }
}
