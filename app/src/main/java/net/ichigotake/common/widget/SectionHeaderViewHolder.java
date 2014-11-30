package net.ichigotake.common.widget;

import android.widget.TextView;

import net.ichigotake.common.util.ViewFinder;

import circlebinder.R;

public final class SectionHeaderViewHolder {

    public static int layoutResourceId = R.layout.common_circle_section_sub_header;

    private final TextView label;

    public SectionHeaderViewHolder(ViewFinder finder) {
        this.label = finder.findOrNull(R.id.common_section_sub_header);
    }

    public TextView getLabel() {
        return label;
    }
}
