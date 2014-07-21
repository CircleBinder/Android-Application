package circlebinder.common.changelog;

import android.view.View;
import android.widget.TextView;

import circlebinder.R;

public final class ChangeLogFeedViewHolder {

    private final TextView title;

    public ChangeLogFeedViewHolder(View convertView) {
        title = (TextView)convertView.findViewById(R.id.circlebinder_list_item);
    }

    public TextView getLabel() {
        return title;
    }

}
