package net.ichigotake.common.app;

import android.content.Context;
import android.net.Uri;

public final class WebBrowserTripper implements Tripper {

    private final Context context;
    private final Uri uri;

    public WebBrowserTripper(Context context, Uri uri) {
        this.context = context;
        this.uri = uri;
    }

    @Override
    public void trip() {
        new ActivityTripper(context, new WebBrowserActivityFactory(uri)).trip();
    }
}
