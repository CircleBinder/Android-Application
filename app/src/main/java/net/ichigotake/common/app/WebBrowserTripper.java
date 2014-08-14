package net.ichigotake.common.app;

import android.content.Context;
import android.net.Uri;

import com.dmitriy.tarasov.android.intents.IntentUtils;

public final class WebBrowserTripper implements Tripper {

    private final Context context;
    private final Uri uri;

    public WebBrowserTripper(Context context, Uri uri) {
        this.context = context;
        this.uri = uri;
    }

    @Override
    public void trip() {
        new ActivityTripper(context, IntentUtils.openLink(uri.toString())).trip();
    }
}
