package circlebinder.creation.enjoy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ichigotake.common.app.ActivityTripper;
import net.ichigotake.common.app.OnClickToTrip;

import circlebinder.creation.app.BaseFragment;
import circlebinder.creation.app.phone.AboutActivity;

public final class PetiOnlyOverviewFragment extends BaseFragment {

    public static PetiOnlyOverviewFragment newInstance() {
        return new PetiOnlyOverviewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(PetiOnlyContainer.layoutResource, parent, false);
        view.setOnClickListener(new OnClickToTrip(
                new ActivityTripper(getActivity(), AboutActivity.createIntent(getActivity())
                )));
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PetiOnlyContainer.render(getView());
    }
}
