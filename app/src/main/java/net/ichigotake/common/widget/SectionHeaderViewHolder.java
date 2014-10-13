package net.ichigotake.common.widget;

import android.widget.TextView;

import circlebinder.R;

public final class SectionHeaderViewHolder {

    public static int layoutResourceId = R.layout.common_section_sub_header;

    private final TextView label;

    public SectionHeaderViewHolder(TextView label) {
        this.label = label;
    }

    public TextView getLabel() {
        return label;
    }
}
