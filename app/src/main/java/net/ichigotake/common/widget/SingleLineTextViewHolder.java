package net.ichigotake.common.widget;

import android.widget.TextView;

import net.ichigotake.common.util.ViewFinder;

import circlebinder.R;

public class SingleLineTextViewHolder {

    private final TextView textView;

    public SingleLineTextViewHolder(ViewFinder finder) {
        this.textView = finder.findOrNull(R.id.common_list_item_label);
    }

    public TextView getTextView() {
        return textView;
    }

}
