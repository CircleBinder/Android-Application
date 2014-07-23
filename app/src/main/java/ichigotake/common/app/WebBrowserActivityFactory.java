package net.ichigotake.common.app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public final class WebBrowserActivityFactory implements ActivityFactory {

    private final Uri uri;

    public WebBrowserActivityFactory(Uri uri) {
        this.uri = uri;
    }

    @Override
    public Intent create(Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_TEXT, uri.toString());
        return intent;
    }

}
