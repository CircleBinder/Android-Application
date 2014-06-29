package circlebinder.creation.system;

import android.os.Bundle;
import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ichigotake.common.app.FragmentFactory;

import circlebinder.common.app.FragmentTripper;
import circlebinder.creation.BaseFragment;
import circlebinder.creation.R;
import jp.yokomark.widget.license.CreditEntry;
import jp.yokomark.widget.license.LicenseCreditView;

public final class OpenSourceLicenseCreditFragment extends BaseFragment {

    public static FragmentTripper tripper(FragmentManager fragmentManager) {
        return new FragmentTripper(fragmentManager, factory());
    }

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
        View view = inflater.inflate(R.layout.circlebinder_fragment_open_source_license, parent, false);
        LicenseCreditView credits = (LicenseCreditView)view.findViewById(
                R.id.circlebinder_fragment_open_source_license_list
        );

        credits.addCredit(new CreditEntry("ActiveAndroid", "Michael Pardo", 2010, CreditEntry.LicenseType.APACHE_V2));
        credits.addCredit(new CreditEntry("CircleBinder Common Library", "ichigotake", 2014, CreditEntry.LicenseType.APACHE_V2));
        credits.addCredit(new CreditEntry("LicenseCreditView", "KeithYokoma", 2014, CreditEntry.LicenseType.APACHE_V2));
        credits.addCredit(new CreditEntry("ltsv4j", "making", 2013, CreditEntry.LicenseType.APACHE_V2));
        credits.addCredit(new CreditEntry("StickyListHeaders", "emilsjolander", 2013, CreditEntry.LicenseType.APACHE_V2));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().setTitle(
                R.string.circlebinder_navigation_open_source_license);
    }
}
