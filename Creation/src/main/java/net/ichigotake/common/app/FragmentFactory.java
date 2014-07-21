package net.ichigotake.common.app;

import android.app.Fragment;

public interface FragmentFactory<T extends Fragment> {

    T create();
}
