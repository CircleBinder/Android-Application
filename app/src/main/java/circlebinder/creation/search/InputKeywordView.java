package circlebinder.creation.search;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import net.ichigotake.common.os.BundleMerger;
import net.ichigotake.common.view.inputmethod.SoftInput;

import circlebinder.R;

public final class InputKeywordView extends FrameLayout {

    private final String KEY_PREFIX = "circlebinder.creation.search.InputKeywordView.";
    private final String KEY_KEYWORD = KEY_PREFIX + "keyword";

    private EditText editText;
    private OnInputTextListener onInputTextListener;

    public InputKeywordView(Context context) {
        super(context);
        initialize();
    }

    public InputKeywordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public InputKeywordView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    private void initialize() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_input_text, this, true);
        editText = (EditText)view.findViewById(R.id.fragment_input_text);
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
        super.onRestoreInstanceState(state);
        if (!(state instanceof Bundle)) {
            return;
        }
        Bundle savedInstanceState = BundleMerger.merge((Bundle)state);
        this.editText.setText(savedInstanceState.getString(KEY_KEYWORD));
    }


    @Override
    protected Parcelable onSaveInstanceState() {
        super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_KEYWORD, editText.getText().toString());
        return bundle;
    }

}
