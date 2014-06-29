package circlebinder.creation.search;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

public final class CircleSearchViewHolder {

    private final AbsListView circles;
    private final View emptyView;

    public CircleSearchViewHolder(AbsListView circles, View emptyView) {
        this.circles = circles;
        this.emptyView = emptyView;
    }

    public AbsListView getCircles() {
        return circles;
    }

    public View getEmptyView() {
        return emptyView;
    }

}
