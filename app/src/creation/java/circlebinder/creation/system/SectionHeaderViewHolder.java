package circlebinder.creation.system;

import android.widget.TextView;

public final class SectionHeaderViewHolder {

    private final TextView label;

    public SectionHeaderViewHolder(TextView label) {
        this.label = label;
    }

    public TextView getLabel() {
        return label;
    }
}
