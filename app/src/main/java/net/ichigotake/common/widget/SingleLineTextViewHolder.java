package net.ichigotake.common.widget;

import android.widget.TextView;

class SingleLineTextViewHolder {

    private final TextView textView;

    SingleLineTextViewHolder(TextView textView) {
        this.textView = textView;
    }

    TextView getTextView() {
        return textView;
    }

}
