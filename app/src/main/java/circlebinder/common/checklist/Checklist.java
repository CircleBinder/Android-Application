package circlebinder.common.checklist;

import java.util.List;

import circlebinder.common.checklist.ChecklistColor;
import circlebinder.common.event.Circle;

public final class Checklist {

    private final List<Circle> circleOverview;
    private final ChecklistColor checklistColor;

    public Checklist(List<Circle> circleOverview, ChecklistColor checklistColor) {
        this.circleOverview = circleOverview;
        this.checklistColor = checklistColor;
    }

    public ChecklistColor getChecklistColor() {
        return checklistColor;
    }

    public List<Circle> getCircleOverview() {
        return circleOverview;
    }

    public String getName() {
        return checklistColor.getName();
    }
}
