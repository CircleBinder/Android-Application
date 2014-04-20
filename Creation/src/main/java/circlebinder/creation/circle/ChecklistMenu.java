package circlebinder.creation.circle;

import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import circlebinder.common.checklist.ChecklistColor;
import circlebinder.creation.R;
import circlebinder.creation.checklist.UpdateChecklistListener;

public final class ChecklistMenu {
    
    private final ActionBarActivity activity;
    private final UpdateChecklistListener listener;
    
    public ChecklistMenu(ActionBarActivity activity, UpdateChecklistListener listener) {
        this.activity = activity;
        this.listener = listener;
    }
    
    public void addMenu(Menu menu, MenuInflater inflater, ChecklistColor checklistColor) {
        inflater.inflate(R.menu.checklist, menu);
        menu.findItem(R.id.circlebinder_menu_checklist).setIcon(checklistColor.getColorDrawable());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.circlebinder_menu_checklist_0:
                listener.update(ChecklistColor.NONE);
                activity.supportInvalidateOptionsMenu();
                return true;
            case R.id.circlebinder_menu_checklist_1:
                listener.update(ChecklistColor.ORANGE);
                activity.supportInvalidateOptionsMenu();
                return true;
            case R.id.circlebinder_menu_checklist_2:
                listener.update(ChecklistColor.PINK);
                activity.supportInvalidateOptionsMenu();
                return true;
            case R.id.circlebinder_menu_checklist_3:
                listener.update(ChecklistColor.YELLOW);
                activity.supportInvalidateOptionsMenu();
                return true;
            case R.id.circlebinder_menu_checklist_4:
                listener.update(ChecklistColor.GREEN);
                activity.supportInvalidateOptionsMenu();
                return true;
            case R.id.circlebinder_menu_checklist_5:
                listener.update(ChecklistColor.LIGHT_BLUE);
                activity.supportInvalidateOptionsMenu();
                return true;
            case R.id.circlebinder_menu_checklist_6:
                listener.update(ChecklistColor.PURPLE);
                activity.supportInvalidateOptionsMenu();
                return true;
            case R.id.circlebinder_menu_checklist_7:
                listener.update(ChecklistColor.BLUE);
                activity.supportInvalidateOptionsMenu();
                return true;
            case R.id.circlebinder_menu_checklist_8:
                listener.update(ChecklistColor.LIGHT_GREEN);
                activity.supportInvalidateOptionsMenu();
                return true;
            case R.id.circlebinder_menu_checklist_9:
                listener.update(ChecklistColor.RED);
                activity.supportInvalidateOptionsMenu();
                return true;
        }
        return false;
    }

        
}
