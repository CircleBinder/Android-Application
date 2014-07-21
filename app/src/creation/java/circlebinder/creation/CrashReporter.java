package circlebinder.creation;

import android.content.Context;

import com.crittercism.app.Crittercism;
import com.crittercism.app.CrittercismConfig;

import circlebinder.BuildConfig;
import circlebinder.common.owner.AppUser;

public final class CrashReporter {

    private CrashReporter() {

    }

    public static void onStart(Context context) {
        AppUser user = new AppUser(context);

        CrittercismConfig config = new CrittercismConfig();
        config.setDelaySendingAppLoad(true);
        config.setCustomVersionName(getCustomVersionName());
        config.setLogcatReportingEnabled(true);
        Crittercism.initialize(context.getApplicationContext(), BuildConfig.CRASH_REPORTER_KEY, config);
        Crittercism.setUsername(user.getUuid().toString());
    }

    private static String getCustomVersionName() {
        return BuildConfig.VERSION_NAME;
    }

}
