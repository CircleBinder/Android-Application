package circlebinder.creation.checklist;

import android.content.Context;
import android.view.ActionProvider;
import android.view.View;

import circlebinder.common.event.Circle;

public final class ChecklistSelectActionProvider extends ActionProvider {

    private final Context context;
    private final Circle circle;

    public ChecklistSelectActionProvider(Context context, Circle circle) {
        super(context);
        this.context = context;
        this.circle = circle;
    }

    @Override
    public View onCreateActionView() {
        ChecklistSelectorView selector = new ChecklistSelectorView(context);
        selector.setCircle(circle);
        return selector;
    }

}
