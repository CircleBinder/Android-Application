package circlebinder.creation.system;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import net.ichigotake.common.worker.ActivityJobWorker;

import circlebinder.common.Legacy;
import circlebinder.common.app.FragmentTripper;
import circlebinder.creation.app.BaseFragment;
import circlebinder.R;

public final class AboutFragment extends BaseFragment implements Legacy {

    private final ActivityJobWorker worker = new ActivityJobWorker();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanseState) {
        View view = inflater.inflate(R.layout.fragment_about, parent, false);
        ListView menuView = (ListView)view.findViewById(R.id.fragment_about_list);
        menuView.setAdapter(new ArrayAdapter<>(
                getActivity(),
                R.layout.common_list_item,
                R.id.common_list_item,
                new String[]{
                        getString(R.string.common_open_source_license),
                }
        ));
        menuView.setOnItemClickListener((parent1, view1, position, id) -> {
            switch (position) {
                case 0:
                    worker.enqueueActivityJob(value -> new FragmentTripper(
                            getFragmentManager(),
                            OpenSourceLicenseCreditFragment.factory()
                    ).trip());
                    break;
                default:
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().setTitle(R.string.common_about);
    }

}
