package net.ichigotake.common.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

public class FragmentPagerAdapter extends FragmentStatePagerAdapter {

    private final FragmentPagerItem item;
    private final SparseArray<Object> registeredPages;

    public FragmentPagerAdapter(FragmentManager fm, FragmentPagerItem item) {
        super(fm);
        this.item = item;
        this.registeredPages = new SparseArray<Object>();
    }

    public void reload(int position) {
        Object page = registeredPages.get(position);
        if (page != null && page instanceof OnPageChangeListener) {
            ((OnPageChangeListener)page).active();
        }
    }

    public void callOnInactive(int position) {
        Object page = registeredPages.get(position);
        if (page != null && page instanceof OnPageChangeListener) {
            ((OnPageChangeListener)page).inactive();
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        registeredPages.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredPages.remove(position);
        super.destroyItem(container, position, object);
    }

    @Override
    public int getItemPosition(Object fragment) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        return item.getItem(position % getCount());
    }

    @Override
    public int getCount() {
        return item.getCount();
    }

    @Override
    public CharSequence getPageTitle(int location) {
        return item.getPageTitle(location);
    }

}
