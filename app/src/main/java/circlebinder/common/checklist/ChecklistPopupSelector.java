package circlebinder.common.checklist;

import android.content.Context;
import android.view.View;
import android.widget.ListPopupWindow;

import net.ichigotake.common.widget.OnItemClickEventListener;
import net.ichigotake.common.widget.OnItemClickListener;

public final class ChecklistPopupSelector {

    private final Context context;
    private final View anchor;
    private final OnItemClickListener<ChecklistColor> listener;
    private final ListPopupWindow popupWindow;

    public ChecklistPopupSelector(Context context, View anchor) {
        this.context = context;
        this.anchor = anchor;
        this.popupWindow = new ListPopupWindow(context);
        this.listener = new OnItemClickListener<>();
    }

    public void setOnItemClickListener(OnItemClickEventListener<ChecklistColor> listener) {
        this.listener.addOnItemClickEventListener(listener);
    }

    public void show() {
        ChecklistSelectorAdapter adapter = new ChecklistSelectorAdapter(context);
        adapter.add(ChecklistColor.NONE);
        for (ChecklistColor item : ChecklistColor.checklists()) {
            adapter.add(item);
        }
        popupWindow.setAdapter(adapter);
        popupWindow.setAnchorView(anchor);
        popupWindow.setModal(true);
        popupWindow.setOnItemClickListener(listener);
        popupWindow.setContentWidth(700);
        popupWindow.show();
    }

    public void dismiss() {
        popupWindow.dismiss();
    }

    public boolean isShowing() {
        return popupWindow.isShowing();
    }
}
