package circlebinder.common.changelog;

import android.view.View;
import android.widget.TextView;

import circlebinder.R;

public final class ChangeLogFeedViewHolder {

    private final TextView title;

    public ChangeLogFeedViewHolder(View convertView) {
        title = (TextView)convertView.findViewById(R.id.common_list_item_label);
    }

    public TextView getLabel() {
        return title;
    }

}
