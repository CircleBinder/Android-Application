package net.ichigotake.common.app;

import android.app.Fragment;

import net.ichigotake.common.lang.InvalidImplementationException;

import java.util.List;

public interface FragmentPagerItemCreator {

    Fragment getItem(int position) throws InvalidImplementationException;

    CharSequence getPageTitle(int position);

    int getCount();

}
