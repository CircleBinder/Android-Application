package circlebinder.creation.enjoy;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ichigotake.common.app.FragmentFactory;
import net.ichigotake.common.app.OnClickToTrip;

import circlebinder.R;
import circlebinder.creation.app.BaseFragment;
import circlebinder.creation.app.phone.WebViewActivity;

public final class PetiOnlyOverviewFragment extends BaseFragment {

    public static FragmentFactory<PetiOnlyOverviewFragment> factory() {
        return PetiOnlyOverviewFragment::newInstance;
    }

    public static PetiOnlyOverviewFragment newInstance() {
        return new PetiOnlyOverviewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(PetiOnlyContainer.layoutResource, parent, false);
        Intent intent = WebViewActivity.createIntent(
                getActivity(), getString(R.string.app_creation_homepage_peti_only)
        );
        view.setOnClickListener(OnClickToTrip.activityTrip(getActivity(), intent));
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PetiOnlyContainer.render(getView());
    }
}
