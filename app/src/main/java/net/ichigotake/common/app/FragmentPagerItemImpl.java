package net.ichigotake.common.app;

import android.app.Fragment;

final class FragmentPagerItemImpl implements FragmentPagerItem {

    private final FragmentFactory<?> fragmentFactory;
    private final CharSequence title;

    FragmentPagerItemImpl(FragmentFactory<?> fragmentFactory, CharSequence title) {
        this.fragmentFactory = fragmentFactory;
        this.title = title;
    }

    @Override
    public Fragment getItem() {
        return fragmentFactory.create();
    }

    @Override
    public CharSequence getTitle() {
        return title;
    }
}
