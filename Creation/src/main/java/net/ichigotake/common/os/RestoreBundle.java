package net.ichigotake.common.os;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import java.io.Serializable;

public final class RestoreBundle {

    private final Bundle bundle;

    public RestoreBundle(Intent intent, Bundle savedInstanceState) {
        this((intent != null) ? intent.getExtras() : null, savedInstanceState);
    }

    public RestoreBundle(Fragment fragment, Bundle savedInstanceState) {
        this(fragment.getArguments(), savedInstanceState);
    }

    public RestoreBundle(Bundle bundle) {
        this.bundle = bundle != null ? bundle : new Bundle();
    }

    private RestoreBundle(Bundle arguments, Bundle savedInstanceState) {
        this(merge(arguments, savedInstanceState));
    }

    private static Bundle merge(Bundle arguments, Bundle savedInstanceState) {
        Bundle mergedBundle;
        if (arguments != null && savedInstanceState != null) {
            arguments.putAll(savedInstanceState);
            mergedBundle = arguments;
        } else if (arguments != null) {
            mergedBundle = arguments;
        } else if (savedInstanceState != null) {
            mergedBundle = savedInstanceState;
        } else {
            mergedBundle = new Bundle();
        }
        return mergedBundle;
    }

    public boolean containsKey(String key) {
        return bundle.containsKey(key);
    }

    public <T extends Parcelable> T getParcelable(String key) {
        T restoreObject;
        if (bundle.containsKey(key)) {
            restoreObject = bundle.getParcelable(key);
        } else {
            restoreObject = null;
        }
        return restoreObject;
    }

    public <T extends Serializable> T getSerializable(String key) {
        T restoreObject;
        if (bundle.containsKey(key)) {
            restoreObject = (T)bundle.getSerializable(key);
        } else {
            restoreObject = null;
        }
        return restoreObject;
    }

    public int getInt(String key) {
        int restoreObject;
        if (bundle.containsKey(key)) {
            restoreObject = bundle.getInt(key);
        } else {
            restoreObject = 0;
        }
        return restoreObject;
    }

    public String getString(String key) {
        return bundle.getString(key);
    }

    public Bundle getBundle() {
        return bundle;
    }
}
