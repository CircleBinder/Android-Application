package circlebinder.common.checklist;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import circlebinder.R;

public final class BlockSelectorViewHolder {

    private final TextView name;
    private final ImageView icon;

    public BlockSelectorViewHolder(View convertView) {
        name = (TextView)convertView.findViewById(R.id.common_spinner_dropdown_item_name);
        icon = (ImageView)convertView.findViewById(R.id.common_spinner_dropdown_item_icon);
    }

    public TextView getName() {
        return name;
    }

    public ImageView getIcon() {
        return icon;
    }
}
