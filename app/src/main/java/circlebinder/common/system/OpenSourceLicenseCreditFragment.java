package circlebinder.common.system;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import net.ichigotake.common.app.FragmentFactory;
import net.ichigotake.common.content.RawResources;

import java.io.IOException;
import java.util.List;

import circlebinder.creation.app.BaseFragment;
import circlebinder.R;

public final class OpenSourceLicenseCreditFragment extends BaseFragment {

    public static FragmentFactory<OpenSourceLicenseCreditFragment> factory() {
        return new FragmentFactory<OpenSourceLicenseCreditFragment>() {
            @Override
            public OpenSourceLicenseCreditFragment create() {
                return newInstance();
            }
        };
    }

    private static OpenSourceLicenseCreditFragment newInstance() {
        return new OpenSourceLicenseCreditFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.common_fragment_open_source_license, parent, false);
        OpenSourceLicenseCreditAdapter adapter = new OpenSourceLicenseCreditAdapter(getActivity());
        String licenseBody = "";
        try {
            List<String> lines = new RawResources(getResources()).getText(R.raw.common_license_apache_v2);
            licenseBody = TextUtils.join("\n", lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        adapter.add(new OpenSourceLicenseCredit("ActiveAndroid", "Michael Pardo", 2010, licenseBody));
        adapter.add(new OpenSourceLicenseCredit("android-intents", "Dmitriy Tarasov ", 2013, licenseBody));
        adapter.add(new OpenSourceLicenseCredit("ltsv4j", "making", 2013, licenseBody));
        adapter.add(new OpenSourceLicenseCredit("Picasso", "Square, Inc", 2013, licenseBody));
        adapter.add(new OpenSourceLicenseCredit("ProgressMenuItem", "Shintaro Katafuchi", 2014, licenseBody));
        adapter.add(new OpenSourceLicenseCredit("StickyListHeaders", "Emil Sjolander", 2013, licenseBody));

        ListView credits = (ListView)view.findViewById(R.id.fragment_open_source_license_list);
        credits.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().setTitle(
                R.string.common_open_source_license);
    }
}
