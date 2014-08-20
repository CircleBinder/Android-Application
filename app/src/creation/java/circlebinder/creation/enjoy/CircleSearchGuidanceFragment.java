package circlebinder.creation.enjoy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ichigotake.common.app.ActivityTripper;
import net.ichigotake.common.app.FragmentFactory;
import net.ichigotake.common.app.OnClickToTrip;

import circlebinder.R;
import circlebinder.creation.app.BaseFragment;
import circlebinder.creation.app.phone.CircleSearchActivity;

public final class CircleSearchGuidanceFragment extends BaseFragment {

    public static FragmentFactory<CircleSearchGuidanceFragment> factory() {
        return CircleSearchGuidanceFragment::newInstance;
    }

    public static CircleSearchGuidanceFragment newInstance() {
        return new CircleSearchGuidanceFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.enjoy_creation_search_guidance, parent, false);
        view.setOnClickListener(new OnClickToTrip(
                new ActivityTripper(getActivity(), CircleSearchActivity.createIntent(getActivity()))
        ));
        return view;
    }

}
