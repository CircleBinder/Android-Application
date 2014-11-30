package circlebinder.common.search;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import net.ichigotake.common.util.Finders;
import net.ichigotake.common.util.ViewFinder;
import net.ichigotake.common.view.inputmethod.SoftInput;

import circlebinder.R;

public final class InputKeywordView extends FrameLayout {

    private EditText editText;
    private OnInputTextListener onInputTextListener;

    public InputKeywordView(Context context) {
        super(context);
    }

    public InputKeywordView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InputKeywordView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        ViewFinder finder = Finders.from(
                LayoutInflater.from(getContext()).inflate(R.layout.common_view_input_keyword, this, true)
        );
        editText = finder.findOrNull(R.id.common_view_input_keyword_edit_text);
        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editText.requestFocus();
                    SoftInput.show(editText);
                } else {
                    editText.clearFocus();
                    SoftInput.hide(editText);
                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (onInputTextListener != null) {
                    onInputTextListener.onTextChange(s.toString());
                }
            }
        });
        editText.setFilters(new InputFilter[]{new EmptyCharacterInputFilter()});
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                SoftInput.hide(v);
                v.clearFocus();
                return false;
            }
        });
    }

    public void setOnInputTextListener(OnInputTextListener listener) {
        this.onInputTextListener = listener;
    }

    public void setText(String keyword) {
        editText.setText(keyword);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Log.d("InputKeywordView", "class "  + state.getClass().getName());
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState savedInstanceState = (SavedState)state;
        this.editText.setText(savedInstanceState.keyword);
        super.onRestoreInstanceState(savedInstanceState.getSuperState());
    }


    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.keyword = editText.getText().toString();
        return savedState;
    }

    static class SavedState extends BaseSavedState implements Parcelable {

        String keyword;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeString(this.keyword);
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel in) {
            super(in);
            this.keyword = in.readString();
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

}
