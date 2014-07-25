package circlebinder.creation.system;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.IOException;

import circlebinder.common.Legacy;
import circlebinder.creation.app.BaseFragment;
import circlebinder.R;

public final class ChangeLogFragment extends BaseFragment implements Legacy {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_log, parent, false);
        ListView changeLogsView = (ListView)view.findViewById(R.id.fragment_change_log_list);
        ChangeLogFeedHeaderAdapter adapter = new ChangeLogFeedHeaderAdapter(getActivity());
        try {
            adapter.addAll(new ChangeLogLoader(getActivity()).load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        changeLogsView.setAdapter(adapter);

        return view;
    }

}
