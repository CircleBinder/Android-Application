package net.ichigotake.common.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import circlebinder.R;

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
        transaction.setCustomAnimations(
                R.animator.common_slide_in,
                R.animator.common_slide_out,
                R.animator.common_slide_in,
                R.animator.common_slide_out
        );
        transaction.replace(targetViewId, nextFragment, mTag);
        if (mAddBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
    
}
