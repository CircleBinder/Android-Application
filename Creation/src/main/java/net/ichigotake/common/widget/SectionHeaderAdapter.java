package net.ichigotake.common.widget;

import android.view.View;
import android.view.ViewGroup;

public interface SectionHeaderAdapter {

    public View getHeaderView(int position, View view, ViewGroup viewGroup);

    public long getHeaderId(int position);

}
