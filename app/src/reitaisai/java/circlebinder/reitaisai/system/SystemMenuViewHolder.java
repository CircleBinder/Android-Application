package circlebinder.reitaisai.system;

import android.view.View;
import android.widget.TextView;

import circlebinder.R;

final class SystemMenuViewHolder {

    static final int layoutResource = R.layout.reitaisai_system_menu_item;

    private final TextView label;

    SystemMenuViewHolder(View view) {
        this.label = (TextView) view;
    }

    TextView getLabel() {
        return label;
    }

}
