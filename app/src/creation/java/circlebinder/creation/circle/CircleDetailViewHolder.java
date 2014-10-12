package circlebinder.creation.circle;

import android.view.View;
import android.widget.TextView;

import circlebinder.common.Legacy;
import circlebinder.R;

public final class CircleDetailViewHolder implements Legacy {

    public static int layoutResource = R.layout.circle_detail_header;

    private final View container;
    private final TextView space;
    private final TextView name;

    public CircleDetailViewHolder(View view) {
        container = view;
        space = (TextView)view.findViewById(R.id.circle_detail_header_space);
        name = (TextView)view.findViewById(R.id.circle_detail_header_name);
    }

    public View getContainer() {
        return container;
    }

    public TextView getSpace() {
        return space;
    }

    public TextView getName() {
        return name;
    }
}
