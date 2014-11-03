package net.ichigotake.common.view;

import android.support.v4.view.*;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public final class MenuPresenter {

    private final MenuInflater inflater;
    private final Menu menu;

    public MenuPresenter(Menu menu, MenuInflater inflater) {
        this.inflater = inflater;
        this.menu = menu;
    }

    public MenuItem inflate(int menuId, int menuItemId) {
        MenuItem item = menu.findItem(menuItemId);
        if (item == null) {
            inflater.inflate(menuId, menu);
            item = menu.findItem(menuItemId);
        }
        return item;
    }

    public void setActionProvider(MenuItem item, android.support.v4.view.ActionProvider actionProvider) {
        MenuItemCompat.setActionProvider(item, actionProvider);
    }

    public void setShowAsAction(MenuItem item, int showAsActionCollapseActionView) {
        MenuItemCompat.setShowAsAction(item, showAsActionCollapseActionView);
    }
}
