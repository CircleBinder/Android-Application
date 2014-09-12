package circlebinder.creation.enjoy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.ichigotake.common.widget.TextViewUtil;

import circlebinder.R;
import circlebinder.creation.app.BaseFragment;

public final class EnjoyCreationFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enjoy_creation, parent, false);
        TextView aboutCatalogLink = (TextView) view.findViewById(R.id.fragment_enjoy_creation_about_catalog_link);
        TextViewUtil.hyperLinkDecoration(aboutCatalogLink);
        PetiOnlyContainer.render(view.findViewById(R.id.fragment_enjoy_creation_peti_only));
        return view;
    }

}
