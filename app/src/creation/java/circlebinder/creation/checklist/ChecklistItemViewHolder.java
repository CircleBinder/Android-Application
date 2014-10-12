package circlebinder.creation.checklist;

import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import circlebinder.R;

public final class ChecklistItemViewHolder {

    private final TextView labelName;
    private final List<TextView> circles;

    public ChecklistItemViewHolder(View convertView) {
        labelName = (TextView) convertView.findViewById(R.id.checklist_item_label_name);
        circles = new CopyOnWriteArrayList<>();
        int circleItemLabelId = R.id.checklist_item_circle_label;
        circles.add((TextView)
                convertView.findViewById(R.id.checklist_item_circle_label1).findViewById(circleItemLabelId)
        );
        circles.add((TextView)
                convertView.findViewById(R.id.checklist_item_circle_label2).findViewById(circleItemLabelId)
        );
        circles.add((TextView)
                convertView.findViewById(R.id.checklist_item_circle_label3).findViewById(circleItemLabelId)
        );
        circles.add((TextView)
                convertView.findViewById(R.id.checklist_item_circle_label4).findViewById(circleItemLabelId)
        );
    }

    public TextView getLabelName() {
        return labelName;
    }

    public List<TextView> getCircles() {
        return circles;
    }
}
