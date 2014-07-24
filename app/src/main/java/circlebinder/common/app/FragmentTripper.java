package circlebinder.common.app;

import android.app.FragmentManager;

import net.ichigotake.common.app.FragmentFactory;
import net.ichigotake.common.app.FragmentTransit;
import net.ichigotake.common.app.Tripper;

/**
 * フラグメント遷移をする
 */
public final class FragmentTripper implements Tripper {

    private int layoutId;
    private String tag;

    public static FragmentTripper firstTrip(FragmentManager fragmentManager, FragmentFactory factory) {
        return new FragmentTripper(fragmentManager, factory)
                .setAddBackStack(false);
    }

    final private FragmentManager fragmentManager;
    final private FragmentFactory factory;
    private boolean addBackStack = true;

    public FragmentTripper(FragmentManager fragmentManager, FragmentFactory factory) {
        this.fragmentManager = fragmentManager;
        this.factory = factory;
    }

    public FragmentTripper setLayoutId(int layoutId) {
        this.layoutId = layoutId;
        return this;
    }

    public FragmentTripper setAddBackStack(boolean added) {
        addBackStack = added;
        return this;
    }

    public FragmentTripper setTag(String tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public void trip() {
        int replaceLayoutId = (layoutId != 0) ? layoutId : android.R.id.content;
        new FragmentTransit(fragmentManager)
                .setAddBackStack(addBackStack)
                .setNextFragment(replaceLayoutId, factory.create())
                .setTag(tag)
                .trip();
    }
}
