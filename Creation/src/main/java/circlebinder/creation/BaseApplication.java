package circlebinder.creation;


import android.app.Application;

import com.activeandroid.ActiveAndroid;

import java.util.Locale;

public final class BaseApplication extends Application {

    public static final Locale APP_LOCALE = Locale.JAPAN;

    @Override
    public void onCreate() {
        super.onCreate();
        CrashReporter.onStart(getApplicationContext());
        ActiveAndroid.initialize(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

}
