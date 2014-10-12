package circlebinder.common.app;

import android.app.Fragment;

import net.ichigotake.common.view.inputmethod.SoftInput;

public abstract class BaseFragment extends Fragment {

    @Override
    public void onResume() {
        super.onResume();
        SoftInput.hide(getView());
    }
}
