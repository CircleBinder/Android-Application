package net.ichigotake.common.view.inputmethod;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public final class SoftInput {

    private static final String LOG_TAG = SoftInput.class.getSimpleName();

    @SuppressWarnings("ConstantConditions")
    public static void hide(View targetView) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager)
                    targetView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    targetView.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS
            );
        } catch (NullPointerException e) {
            Log.e(LOG_TAG, "", e);
        }

    }
}
