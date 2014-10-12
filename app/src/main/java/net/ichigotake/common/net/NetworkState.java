package net.ichigotake.common.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

public final class NetworkState {

    private static final String LOG_TAG = "NetworkState";

    private NetworkState() {}

    public static boolean isConnected(Context context) {
        if (context == null) {
            return false;
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            return connectivityManager.getActiveNetworkInfo().isConnected();
        } catch (NullPointerException e) {
            Log.d(LOG_TAG, "", e);
            return false;
        }
    }

}
