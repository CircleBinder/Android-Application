package circlebinder.creation.initialize;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import circlebinder.R;

/**
 * アプリ内データのローカルストレージ
 */
public final class AppStorage {

    private final SharedPreferences pref;
    private final Context context;

    public AppStorage(Context context) {
        this.context = context;
        this.pref = context.getSharedPreferences(
                context.getString(R.string.circlebinder_pref_app_storage), Context.MODE_MULTI_PROCESS);
    }

    public boolean isInitialized() {
        return pref.getBoolean(context.getString(R.string.circlebinder_pref_app_storage_is_initialize), false);
    }

    @SuppressLint("CommitPrefEdits")
    public void setInitialized(boolean complete) {
        pref.edit()
                .putBoolean(context.getString(R.string.circlebinder_pref_app_storage_is_initialize), complete)
                .apply();
    }
}
