package circlebinder.creation.initialize;

import android.content.Context;
import android.content.SharedPreferences;

import circlebinder.R;

public final class LegacyAppStorage {

    private final SharedPreferences pref;
    private final Context context;

    public LegacyAppStorage(Context context) {
        this.context = context;
        this.pref = context.getSharedPreferences(
                context.getString(R.string.app_pref_app_storage), Context.MODE_MULTI_PROCESS);
    }

    public boolean isInitialized() {
        return pref.getBoolean(context.getString(R.string.app_pref_app_storage_is_initialize), false);
    }

    public void setInitialized(boolean complete) {
        pref.edit()
                .putBoolean(context.getString(R.string.app_pref_app_storage_is_initialize), complete)
                .apply();
    }

}
