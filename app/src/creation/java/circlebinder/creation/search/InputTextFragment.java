package circlebinder.creation.search;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import net.ichigotake.common.os.BundleMerger;
import net.ichigotake.common.view.inputmethod.SoftInput;

import circlebinder.creation.app.BaseFragment;
import circlebinder.R;

public final class InputTextFragment extends BaseFragment {

    private static final String KEY_KEYWORD = "keyword";

    private EditText editText;
    private OnInputTextListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof OnInputTextListener)) {
            throw new IllegalStateException("Activity must implements OnInputTextListener.");
        }
        this.listener = (OnInputTextListener)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_text, parent, false);
        editText = (EditText)view.findViewById(R.id.fragment_input_text);
        editText.setText(BundleMerger.merge(getArguments(), savedInstanceState).getString(KEY_KEYWORD));
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                editText.requestFocus();
            } else {
                editText.clearFocus();
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listener.onTextChange(s.toString());
            }
        });
        editText.setOnEditorActionListener((v, actionId, event) -> {
            SoftInput.hide(v);
            v.clearFocus();
            return false;
        });
        return view;
    }

    public void setText(String keyword) {
        editText.setText(keyword);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_KEYWORD, editText.getText().toString());
    }

    @Override
    public void onDetach() {
        this.listener = null;
        super.onDetach();
    }

}
