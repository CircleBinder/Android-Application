package circlebinder.common.circle;

import android.view.View;
import android.widget.TextView;

import net.ichigotake.common.util.Finders;
import net.ichigotake.common.util.ViewFinder;

import circlebinder.R;
import circlebinder.common.checklist.ChecklistSelectorView;

public final class CircleViewHolder {

    private final TextView circleName;
    private final TextView penName;
    private final TextView genre;
    private final ChecklistSelectorView checklistSelector;
    private final TextView space;
    private final View spaceContainer;

    public CircleViewHolder(View container) {
        ViewFinder finder = Finders.from(container);
        this.circleName = finder.findOrNull(R.id.common_circle_list_item_name);
        this.penName = finder.findOrNull(R.id.common_circle_list_item_pen_name);
        this.genre = finder.findOrNull(R.id.common_circle_list_item_genre);
        this.checklistSelector = finder.findOrNull(R.id.common_circle_list_checklist);
        this.space = finder.findOrNull(R.id.common_circle_list_item_space);
        this.spaceContainer = finder.findOrNull(R.id.common_circle_list_item_space_container);
    }

    public TextView getCircleName() {
        return circleName;
    }

    public TextView getPenName() {
        return penName;
    }

    public TextView getGenre() {
        return genre;
    }

    public ChecklistSelectorView getChecklistSelector() {
        return checklistSelector;
    }

    public TextView getSpace() {
        return space;
    }

    public View getSpaceContainer() {
        return spaceContainer;
    }

}
