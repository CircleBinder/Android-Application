package circlebinder.creation.system;

import android.os.Bundle;
import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ichigotake.common.app.FragmentFactory;

import java.io.IOException;

import circlebinder.Legacy;
import circlebinder.common.app.FragmentTripper;
import circlebinder.creation.BaseFragment;
import circlebinder.creation.R;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public final class ChangeLogFragment extends BaseFragment implements Legacy {

    public static FragmentTripper tripper(FragmentManager fragmentManager) {
        return new FragmentTripper(fragmentManager, factory());
    }

    public static FragmentFactory<ChangeLogFragment> factory() {
        return new FragmentFactory<ChangeLogFragment>() {
            @Override
            public ChangeLogFragment create() {
                return newInstance();
            }
        };
    }

    private static ChangeLogFragment newInstance() {
        return new ChangeLogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.circlebinder_fragment_changelog, parent, false);
        StickyListHeadersListView changeLogsView = (StickyListHeadersListView)view.findViewById(
                R.id.circlebinder_fragment_changelog_list
        );
        ChangeLogFeedHeaderAdapter adapter = new ChangeLogFeedHeaderAdapter(getActivity());
        try {
            adapter.addAll(new ChangeLogLoader(getActivity()).load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        changeLogsView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().setTitle(R.string.circlebinder_navigation_change_log);
    }


}
