package circlebinder.common.search;

import android.content.Context;
import android.content.SharedPreferences;

public final class SearchFormStore {

    private final String KEY_FORM_VISIBILITY = "search_form_store_form_visibility";
    private final String KEY_KEYWORD = "search_form_store_form_keyword";
    private final SharedPreferences pref;

    public SearchFormStore(Context context) {
        this.pref = context.getApplicationContext()
                .getSharedPreferences("search_form_store", Context.MODE_PRIVATE);
    }

    public boolean isFormVisible() {
        return pref.getBoolean(KEY_FORM_VISIBILITY, false);
    }

    public void setFormVisible(boolean visible) {
        pref.edit()
                .putBoolean(KEY_FORM_VISIBILITY, visible)
                .apply();
    }

    public String getKeyword() {
        return pref.getString(KEY_KEYWORD, "");
    }

    public void setKeyword(String keyword) {
        pref.edit()
                .putString(KEY_KEYWORD, keyword)
                .apply();
    }

}
