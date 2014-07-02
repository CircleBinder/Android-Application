package circlebinder.creation.search;

import android.view.View;
import android.widget.ListView;

public final class CircleSearchViewHolder {

    private final ListView circles;
    private final View emptyView;

    public CircleSearchViewHolder(ListView circles, View emptyView) {
        this.circles = circles;
        this.emptyView = emptyView;
    }

    public ListView getCircles() {
        return circles;
    }

    public View getEmptyView() {
        return emptyView;
    }

}
