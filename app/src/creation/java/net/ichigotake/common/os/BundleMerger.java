package net.ichigotake.common.os;

import android.content.Intent;
import android.os.Bundle;

public final class BundleMerger {

    public static Bundle merge(Intent intent, Bundle... bundles) {
        Bundle mergedBundle = new Bundle();
        if (intent != null && intent.getExtras() != null) {
            mergedBundle.putAll(intent.getExtras());
        }
        for (Bundle item : bundles) {
            if (item == null) {
                continue;
            }
            mergedBundle.putAll(item);
        }
        return mergedBundle;
    }

    public static Bundle merge(Bundle... bundles) {
        Bundle mergedBundle = new Bundle();
        for (Bundle item : bundles) {
            if (item == null) {
                continue;
            }
            mergedBundle.putAll(item);
        }
        return mergedBundle;
    }
}
