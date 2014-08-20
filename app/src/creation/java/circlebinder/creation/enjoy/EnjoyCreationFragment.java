package circlebinder.creation.enjoy;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmitriy.tarasov.android.intents.IntentUtils;

import net.ichigotake.common.app.ActivityTripper;
import net.ichigotake.common.app.FragmentFactory;
import net.ichigotake.common.app.OnClickToTrip;

import circlebinder.R;
import circlebinder.creation.app.BaseFragment;

public final class EnjoyCreationFragment extends BaseFragment {

    public static FragmentFactory<EnjoyCreationFragment> factory() {
        return EnjoyCreationFragment::newInstance;
    }

    public static EnjoyCreationFragment newInstance() {
        return new EnjoyCreationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome_to_creation, parent, false);
        Intent openLinkIntent = IntentUtils.openLink(getString(R.string.app_creation_homepage_welcome));
        view.setOnClickListener(new OnClickToTrip(new ActivityTripper(getActivity(), openLinkIntent)));
        return view;
    }

}
