package circlebinder.common.card;

import android.view.View;
import android.widget.TextView;

import circlebinder.R;

public final class HomeCardItemViewHolder {

    private final TextView labelName;
    private final TextView caption;

    public HomeCardItemViewHolder(View convertView) {
        labelName = (TextView) convertView.findViewById(R.id.common_home_card_item_label);
        caption = (TextView) convertView.findViewById(R.id.common_home_card_item_caption);
    }

    public TextView getLabel() {
        return labelName;
    }

    public TextView getCaption() {
        return caption;
    }
}
