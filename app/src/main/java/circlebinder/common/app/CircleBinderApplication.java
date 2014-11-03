package circlebinder.common.app;


import android.app.Application;

import com.activeandroid.ActiveAndroid;

import java.util.Locale;

public class CircleBinderApplication extends Application {

    public static CircleBinderApplication get() {
        return instance;
    }

    public static final Locale APP_LOCALE = Locale.JAPAN;
    private static CircleBinderApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        CrashReporter.onStart(getApplicationContext());
        ActiveAndroid.initialize(this);
    }

    @Override
    public void onTerminate() {
        instance = null;
        super.onTerminate();
        ActiveAndroid.dispose();
    }

}
