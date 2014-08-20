package net.ichigotake.common.app;

public final class FragmentPagerItemFactory {

    public static FragmentPagerItem create(FragmentFactory<?> fragmentFactory, CharSequence title) {
        return new FragmentPagerItemImpl(fragmentFactory, title);
    }

}
