package net.ichigotake.common.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public final class FragmentTransit implements Tripper {

    final private FragmentManager mFragmentManager;
    
    private boolean mAddBackStack = true;
    private Fragment mNextFragment;
    private int mTargetViewId;
    private String mTag;
    
    public FragmentTransit(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }
    
    public FragmentTransit setNextFragment(int targetViewId, Fragment nextFragment) {
        mTargetViewId = targetViewId;
        mNextFragment = nextFragment;
        return this;
    }

    public FragmentTransit setAddBackStack(boolean flag) {
        mAddBackStack = flag;
        return this;
    }

    public FragmentTransit setTag(String tag) {
        mTag = tag;
        return this;
    }

    @Override
    public void trip() {
        final int targetViewId = mTargetViewId;
        final Fragment nextFragment = mNextFragment;

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(targetViewId, nextFragment, mTag);
        if (mAddBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
    
}
