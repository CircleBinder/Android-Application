package net.ichigotake.common.widget;


public interface OnItemSelectedEventListener<T> {

    void onItemSelected(T item);

    void onNothingSelected();
}
