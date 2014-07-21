package circlebinder.common.owner;

import android.content.Context;
import android.content.SharedPreferences;

import circlebinder.Legacy;

import java.util.UUID;

/**
 * アプリユーザーのデータ
 */
public class AppUser implements Legacy {

    final private SharedPreferences mPref;
    final private String KEY_UUID = "appuser-uuid";

    public AppUser(Context context) {
        mPref = context.getSharedPreferences("circlebinder-appuser", Context.MODE_PRIVATE);
    }

    public UUID getUuid() {
        final String uuidString = mPref.getString(KEY_UUID, null);
        final UUID uuid;
        if (uuidString != null) {
            uuid = UUID.fromString(uuidString);
        } else {
            uuid = UUID.randomUUID();
            setUuid(uuid.toString());
        }
        return uuid;
    }

    private void setUuid(String uuid) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString(KEY_UUID, uuid);
        editor.commit();
    }

}
