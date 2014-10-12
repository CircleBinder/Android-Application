package net.ichigotake.common.widget;

import net.ichigotake.common.app.Tripper;

public class ListItemTripFactory<ITEM> {

    public ListItemTrip<ITEM> create(ITEM item, Tripper tripper) {
        return ListItemTripImpl.create(item, tripper);
    }
}
