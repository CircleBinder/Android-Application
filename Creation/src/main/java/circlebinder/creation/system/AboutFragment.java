package circlebinder.creation.system;

import android.os.Bundle;
import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import net.ichigotake.common.app.FragmentFactory;

import circlebinder.Legacy;
import circlebinder.common.app.FragmentTripper;
import circlebinder.creation.BaseFragment;
import circlebinder.creation.R;

public final class AboutFragment extends BaseFragment implements Legacy {

    public static FragmentTripper tripper(FragmentManager fragmentManager) {
        return new FragmentTripper(fragmentManager, factory());
    }

    public static FragmentFactory<AboutFragment> factory() {
        return new FragmentFactory<AboutFragment>() {
            @Override
            public AboutFragment create() {
                return newInstance();
            }
        };
    }

    private static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanseState) {
        View view = inflater.inflate(R.layout.circlebinder_fragment_about, parent, false);
        ListView menuView = (ListView)view.findViewById(R.id.circlebinder_fragment_about_list);
        menuView.setAdapter(new ArrayAdapter<String>(
                getActivity(),
                R.layout.circlebinder_list_item,
                R.id.circlebinder_list_item,
                new String[]{
                        getString(R.string.circlebinder_navigation_open_source_license),
                }
        ));
        menuView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        OpenSourceLicenseCreditFragment
                                .tripper(getFragmentManager())
                                .setLayoutId(R.id.activity_fragment_content)
                                .trip();
                        break;
                    default:
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().setTitle(R.string.circlebinder_navigation_about);
    }

}
