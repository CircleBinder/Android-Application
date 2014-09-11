package circlebinder.creation.search;

import android.content.Context;
import android.content.SharedPreferences;

final class SearchFormStore {

    private final String KEY_FORM_VISIBILITY = "search_form_store_form_visibility";
    private final String KEY_KEYWORD = "search_form_store_form_keyword";
    private final SharedPreferences pref;

    SearchFormStore(Context context) {
        this.pref = context.getApplicationContext()
                .getSharedPreferences("search_form_store", Context.MODE_PRIVATE);
    }

    boolean isFormVisible() {
        return pref.getBoolean(KEY_FORM_VISIBILITY, true);
    }

    void setFormVisible(boolean visible) {
        pref.edit()
                .putBoolean(KEY_FORM_VISIBILITY, visible)
                .apply();
    }

    String getKeyword() {
        return pref.getString(KEY_KEYWORD, "");
    }

    void setKeyword(String keyword) {
        pref.edit()
                .putString(KEY_KEYWORD, keyword)
                .apply();
    }

}
