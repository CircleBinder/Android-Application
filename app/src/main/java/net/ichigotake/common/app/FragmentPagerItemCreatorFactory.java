package net.ichigotake.common.app;

import java.util.List;

public final class FragmentPagerItemCreatorFactory {

    public static FragmentPagerItemCreator create(List<FragmentPagerItem> items) {
        return new FragmentPagerItemCreatorImpl(items);
    }

}
