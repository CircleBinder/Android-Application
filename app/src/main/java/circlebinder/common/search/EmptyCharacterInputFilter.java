package circlebinder.common.search;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

public final class EmptyCharacterInputFilter implements InputFilter {

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        if (TextUtils.isEmpty(dest.toString().trim()) && TextUtils.isEmpty(source.toString().trim())) {
            return "";
        }
        return source;
    }
}
