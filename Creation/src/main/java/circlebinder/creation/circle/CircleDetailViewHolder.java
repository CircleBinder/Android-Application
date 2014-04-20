package circlebinder.creation.circle;

import android.view.View;
import android.widget.TextView;

import circlebinder.Legacy;
import circlebinder.creation.R;

public final class CircleDetailViewHolder implements Legacy {

    private final TextView space;
    private final TextView name;

    public CircleDetailViewHolder(View view) {
        space = (TextView)view.findViewById(R.id.circlebinder_actionbar_circle_detail_space);
        name = (TextView)view.findViewById(R.id.circlebinder_actionbar_circle_detail_name);
    }

    public TextView getSpace() {
        return space;
    }

    public TextView getName() {
        return name;
    }
}
