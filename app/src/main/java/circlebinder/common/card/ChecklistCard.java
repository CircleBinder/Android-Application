package circlebinder.common.card;

import android.content.Context;
import android.content.Intent;

import circlebinder.common.app.phone.ChecklistActivity;
import circlebinder.common.checklist.ChecklistColor;
import circlebinder.common.event.Circle;

final class ChecklistCard implements HomeCard {

    private final ChecklistColor color;
    private final CharSequence label;
    private final CharSequence caption;

    ChecklistCard(Circle circle) {
        this.color = circle.getChecklistColor();
        this.label = circle.getChecklistColor().getId() + " " + circle.getChecklistColor().getName();
        this.caption = circle.getSpace().getSimpleName() + " " + circle.getName();
    }

    @Override
    public CharSequence getLabel() {
        return label;
    }

    @Override
    public CharSequence getCaption() {
        return caption;
    }

    @Override
    public int getBackgroundResource() {
        return color.getColorResource();
    }

    @Override
    public Intent createTransitIntent(Context context) {
        return ChecklistActivity.createIntent(context, color);
    }
}
