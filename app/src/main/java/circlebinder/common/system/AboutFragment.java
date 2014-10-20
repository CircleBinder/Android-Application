package circlebinder.common.system;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import net.ichigotake.common.app.ActivityNavigation;
import net.ichigotake.common.app.Tripper;
import net.ichigotake.common.widget.SingleLineTextTripAdapter;
import net.ichigotake.common.worker.ActivityJob;
import net.ichigotake.common.worker.ActivityJobWorker;
import net.ichigotake.common.worker.ActivityJobWorkerClient;

import circlebinder.common.Legacy;
import circlebinder.common.app.FragmentTripper;
import circlebinder.common.app.BaseFragment;
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
        View view = inflater.inflate(R.layout.common_fragment_about_application, parent, false);
        ListView menuView = (ListView)view.findViewById(R.id.view_about_application_list);
        SingleLineTextTripAdapter adapter = new SingleLineTextTripAdapter(getActivity());
        adapter.add(
                getString(R.string.common_open_source_license),
                new Tripper() {
                    @Override
                    public void trip() {
                        worker.enqueueActivityJob(new ActivityJob() {
                            @Override
                            public void run(ActionBarActivity value) {
                                new FragmentTripper(
                                        value.getSupportFragmentManager(),
                                        OpenSourceLicenseCreditFragment.factory()
                                ).trip();                            }
                        });
                    }
                });
        menuView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ActivityNavigation.getSupportActionBar(getActivity()).setTitle(R.string.common_about);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.worker = null;
    }

}
