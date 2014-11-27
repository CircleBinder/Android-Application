package circlebinder.common.card;

import android.view.View;
import android.widget.TextView;

import net.ichigotake.common.util.Finders;
import net.ichigotake.common.util.ViewFinder;

import circlebinder.R;

public final class HomeCardItemViewHolder {

    private final TextView labelName;
    private final TextView caption;

    public HomeCardItemViewHolder(View container) {
        ViewFinder finder = Finders.from(container);
        labelName = finder.find(R.id.common_home_card_item_label);
        caption = finder.find(R.id.common_home_card_item_caption);
    }

    public TextView getLabel() {
        return labelName;
    }

    public TextView getCaption() {
        return caption;
    }
}
