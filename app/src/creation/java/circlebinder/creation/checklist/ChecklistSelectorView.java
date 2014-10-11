package circlebinder.creation.checklist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import circlebinder.R;
import circlebinder.common.checklist.ChecklistColor;
import circlebinder.common.checklist.ChecklistPopupSelector;
import circlebinder.common.event.Circle;
import circlebinder.creation.app.BroadcastEvent;
import circlebinder.creation.event.CircleTable;

public class ChecklistSelectorView extends FrameLayout {

    private View checklistColorView;
    private ChecklistPopupSelector selector;
    private View anchor;
    private Circle circle;

    @SuppressWarnings("unused") // Public API
    public ChecklistSelectorView(Context context) {
        super(context);
        if (!isInEditMode()) {
            initialize();
        }
    }

    @SuppressWarnings("unused") // Public API
    public ChecklistSelectorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            initialize();
        }
    }

    @SuppressWarnings("unused") // Public API
    public ChecklistSelectorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()) {
            initialize();
        }
    }

    private void initialize() {
        if (isInEditMode()) {
            return;
        }
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_checklist_selector, this, true);
        this.checklistColorView = view.findViewById(R.id.view_checklist_selector_label);
        this.selector = new ChecklistPopupSelector(getContext());
        this.anchor = checklistColorView;
        checklistColorView.setOnClickListener(v -> {
            if (selector.isShowing()) {
                selector.dismiss();
            } else {
                selector.show(anchor);
            }
        });
        selector.setOnItemClickListener(item -> {
            updateChecklistColor(circle, item);
            CircleTable.setChecklist(circle, item);
            getContext().sendBroadcast(BroadcastEvent.createIntent());
            selector.dismiss();
        });
    }

    public void setPopupAnchor(View anchor) {
        this.anchor = anchor;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
        updateChecklistColor(circle, circle.getChecklistColor());
    }

    private void updateChecklistColor(Circle circle, ChecklistColor checklistColor) {
        if (circle == null) {
            return;
        }
        checklistColorView.setBackgroundResource(checklistColor.getDrawableResource());
    }

    public void showPopup() {
        selector.show(anchor);
    }
}
