package net.ichigotake.common.widget;

import net.ichigotake.common.app.Tripper;

class ListItemTripImpl<ITEM> implements ListItemTrip<ITEM> {

    static <ITEM> ListItemTrip<ITEM> create(ITEM item, Tripper tripper) {
        return new ListItemTripImpl<>(item, tripper);
    }

    private final ITEM item;
    private final Tripper tripper;

    public ListItemTripImpl(ITEM item, Tripper tripper) {
        this.item = item;
        this.tripper = tripper;
    }

    @Override
    public ITEM getItem() {
        return item;
    }

    @Override
    public Tripper getTripper() {
        return tripper;
    }
}
