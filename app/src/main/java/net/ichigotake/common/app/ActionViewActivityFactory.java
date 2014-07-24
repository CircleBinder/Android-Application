package net.ichigotake.common.app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public final class ActionViewActivityFactory implements ActivityFactory {

    private final Uri uri;

    public ActionViewActivityFactory(Uri uri) {
        this.uri = uri;
    }

    @Override
    public Intent create(Context context) {
        return new Intent(Intent.ACTION_VIEW, uri);
    }
}
