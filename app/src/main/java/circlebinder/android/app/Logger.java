package circlebinder.android.app;

import android.util.Log;

public class Logger {

    public static void debug(String tag, String message) {
        Log.d(tag, message);
    }

    public static void error(String tag, Throwable throwable) {
        Log.e(tag, "", throwable);
    }
}
