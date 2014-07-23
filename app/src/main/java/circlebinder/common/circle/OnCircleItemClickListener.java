package circlebinder.common.circle;

import circlebinder.common.event.Circle;

public interface OnCircleItemClickListener {

    void onSpaceClick(CircleViewHolder viewHolder, int position, Circle item);
}
