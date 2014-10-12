package circlebinder.creation.system;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import net.ichigotake.common.widget.SingleLineTextTripAdapter;
import net.ichigotake.common.worker.ActivityJobWorker;
import net.ichigotake.common.worker.ActivityJobWorkerClient;

import circlebinder.common.Legacy;
import circlebinder.common.app.FragmentTripper;
import circlebinder.common.system.OpenSourceLicenseCreditFragment;
import circlebinder.creation.app.BaseFragment;
import circlebinder.R;

public final class AboutFragment extends BaseFragment implements Legacy {

    private ActivityJobWorker worker;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof ActivityJobWorkerClient)) {
            throw new IllegalStateException("Activity must implements ActivityJobWorkerClient.");
        }
        this.worker = ((ActivityJobWorkerClient)activity).getWorker();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanseState) {
        View view = inflater.inflate(R.layout.fragment_about_application, parent, false);
        ListView menuView = (ListView)view.findViewById(R.id.view_about_application_list);
        SingleLineTextTripAdapter adapter = new SingleLineTextTripAdapter(getActivity());
        adapter.add(
                getString(R.string.common_open_source_license),
                () -> worker.enqueueActivityJob(value -> new FragmentTripper(
                        value.getFragmentManager(),
                        OpenSourceLicenseCreditFragment.factory()
                ).trip()));
        menuView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().setTitle(R.string.common_about);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.worker = null;
    }

}
