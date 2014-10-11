package net.ichigotake.common.widget;

import net.ichigotake.common.app.Tripper;

public interface ListItemTrip<ITEM> {

    ITEM getItem();

    Tripper getTripper();
}
