package circlebinder.creation.enjoy;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.ichigotake.common.app.FragmentFactory;
import net.ichigotake.common.app.OnClickToTrip;
import net.ichigotake.common.widget.TextViewUtil;

import circlebinder.R;
import circlebinder.creation.app.BaseFragment;
import circlebinder.creation.app.phone.WebViewActivity;

public final class EnjoyCreationFragment extends BaseFragment {

    public static FragmentFactory<EnjoyCreationFragment> factory() {
        return EnjoyCreationFragment::newInstance;
    }

    public static EnjoyCreationFragment newInstance() {
        return new EnjoyCreationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enjoy_creation, parent, false);
        TextView aboutCatalogLink = (TextView) view.findViewById(R.id.fragment_enjoy_creation_about_catalog_link);
        TextViewUtil.hyperLinkDecoration(aboutCatalogLink);
        String aboutCatalogUrl = getString(R.string.app_enjoy_creation_about_catalog_link);
        Intent intent = WebViewActivity.createIntent(getActivity(), aboutCatalogUrl);
        aboutCatalogLink.setOnClickListener(OnClickToTrip.activityTrip(getActivity(), intent));
        return view;
    }

}
