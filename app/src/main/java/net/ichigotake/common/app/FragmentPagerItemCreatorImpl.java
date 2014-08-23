package net.ichigotake.common.app;

import android.app.Fragment;

import net.ichigotake.common.lang.InvalidImplementationException;

import java.util.List;

final class FragmentPagerItemCreatorImpl implements FragmentPagerItemCreator {

    private final List<FragmentPagerItem> items;

    FragmentPagerItemCreatorImpl(List<FragmentPagerItem> items) {
        this.items = items;
    }

    @Override
    public Fragment getItem(int position) throws InvalidImplementationException {
        return items.get(position).getItem();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return items.get(position).getTitle();
    }

    @Override
    public int getCount() {
        return items.size();
    }

}
