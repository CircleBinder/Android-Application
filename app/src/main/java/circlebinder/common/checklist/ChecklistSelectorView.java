package circlebinder.common.checklist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import net.ichigotake.common.util.Finders;
import net.ichigotake.common.util.ViewFinder;
import net.ichigotake.common.view.inputmethod.SoftInput;
import net.ichigotake.common.widget.OnItemClickEventListener;

import circlebinder.R;
import circlebinder.common.event.Circle;
import circlebinder.common.app.BroadcastEvent;
import circlebinder.common.table.EventCircleTable;

public class ChecklistSelectorView extends FrameLayout {

    private TextView checklistColorView;
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
        ViewFinder finder = Finders.from(
                LayoutInflater.from(getContext())
                        .inflate(R.layout.common_view_checklist_selector, this, true)
        );
        this.checklistColorView = finder.find(R.id.common_view_checklist_selector_label);
        this.selector = new ChecklistPopupSelector(getContext());
        this.anchor = checklistColorView;
        checklistColorView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circle == null || selector.isShowing()) {
                    selector.dismiss();
                } else {
                    selector.show(anchor);
                }
            }
        });
        selector.setOnItemClickListener(new OnItemClickEventListener<ChecklistColor>() {
            @Override
            public void onItemClick(ChecklistColor item) {
                updateChecklistColor(item, circle);
                EventCircleTable.setChecklist(circle, item);
                getContext().sendBroadcast(BroadcastEvent.createIntent());
                selector.dismiss();
            }
        });
    }

    public void setPopupAnchor(View anchor) {
        this.anchor = anchor;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
        updateChecklistColor(circle.getChecklistColor(), circle);
    }

    public void setChecklist(ChecklistColor color) {
        checklistColorView.setBackgroundResource(color.getDrawableResource());
        checklistColorView.setText("" + color.getId());
    }

    private void updateChecklistColor(ChecklistColor checklistColor, Circle circle) {
        setChecklist(checklistColor);
        if (circle == null) {
            return;
        }
        if (ChecklistColor.isChecklist(checklistColor)) {
            checklistColorView.setText("" + checklistColor.getId());
        } else {
            checklistColorView.setText("");
        }
    }

    public void showPopup() {
        SoftInput.hide(this);
        selector.show(anchor);
    }
}
