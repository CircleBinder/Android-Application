package circlebinder.creation.checklist;

import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import circlebinder.creation.R;

public final class ChecklistItemViewHolder {

    private final View labelBackground;
    private final TextView labelName;
    private final List<TextView> circles;

    public ChecklistItemViewHolder(View convertView) {
        labelBackground = convertView.findViewById(R.id.circlebinder_checklist_item_label_background);
        labelName = (TextView) convertView.findViewById(R.id.circlebinder_checklist_item_label_name);
        circles = new CopyOnWriteArrayList<TextView>();
        circles.add((TextView)convertView.findViewById(R.id.circlebinder_checklist_item_circle_label1));
        circles.add((TextView)convertView.findViewById(R.id.circlebinder_checklist_item_circle_label2));
        circles.add((TextView)convertView.findViewById(R.id.circlebinder_checklist_item_circle_label3));
        circles.add((TextView)convertView.findViewById(R.id.circlebinder_checklist_item_circle_label4));
    }

    public View getLabelBackground() {
        return labelBackground;
    }

    public TextView getLabelName() {
        return labelName;
    }

    public List<TextView> getCircles() {
        return circles;
    }
}
