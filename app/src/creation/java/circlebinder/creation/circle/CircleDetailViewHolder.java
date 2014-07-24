package circlebinder.creation.circle;

import android.view.View;
import android.widget.TextView;

import circlebinder.common.Legacy;
import circlebinder.R;

public final class CircleDetailViewHolder implements Legacy {

    private final TextView space;
    private final TextView name;

    public CircleDetailViewHolder(View view) {
        space = (TextView)view.findViewById(R.id.circle_detail_header_space);
        name = (TextView)view.findViewById(R.id.circle_detail_header_name);
    }

    public TextView getSpace() {
        return space;
    }

    public TextView getName() {
        return name;
    }
}
