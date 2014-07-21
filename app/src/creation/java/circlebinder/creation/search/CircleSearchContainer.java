package circlebinder.creation.search;

import android.content.Context;

import circlebinder.common.circle.CircleAdapter;
import circlebinder.common.search.CircleSearchOption;

public final class CircleSearchContainer {

    private final CircleSearchViewHolder holder;
    private final CircleAdapter adapter;

    public CircleSearchContainer(Context context, CircleSearchViewHolder holder) {
        this.holder = holder;
        this.adapter = new CircleAdapter(
                context,
                null,
                new CircleCursorConverter());
        this.holder.getCircles().setAdapter(adapter);
    }

    public CircleSearchViewHolder getViewHolder() {
        return holder;
    }

    public void reload(CircleSearchOption searchOption) {
        adapter.setFilterQueryProvider(new CircleQueryProvider(searchOption));
        adapter.getFilter().filter("");
    }

    public void setPosition(int position) {
        holder.getCircles().setSelection(position);
    }
}
