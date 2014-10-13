package circlebinder.common.circle;

import android.view.View;
import android.widget.TextView;

import circlebinder.common.Legacy;
import circlebinder.R;

//TODO: カスタムビューにしよう
public final class CircleDetailViewHolder implements Legacy {

    public static int layoutResource = R.layout.common_circle_detail_header;

    private final View container;
    private final TextView space;
    private final TextView name;

    public CircleDetailViewHolder(View view) {
        container = view;
        space = (TextView)view.findViewById(R.id.common_circle_detail_header_space);
        name = (TextView)view.findViewById(R.id.common_circle_detail_header_name);
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
