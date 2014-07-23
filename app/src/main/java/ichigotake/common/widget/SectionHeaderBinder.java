package net.ichigotake.common.widget;

import android.view.LayoutInflater;
import android.view.View;

public interface SectionHeaderBinder<ITEM, ITEM_TAG, HEADER_TAG> extends ViewBinder<ITEM, ITEM_TAG> {

    HEADER_TAG generateHeaderTag(int position, ITEM item, View convertView);

    View generateHeaderView(int position, ITEM item, LayoutInflater inflater);

    void bindHeaderView(int position, ITEM item, HEADER_TAG tag);

    long getHeaderId(int position, ITEM item);
}
