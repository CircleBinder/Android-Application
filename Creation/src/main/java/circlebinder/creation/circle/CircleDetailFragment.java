package circlebinder.creation.circle;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import net.ichigotake.common.app.ActionSendFilterActionProvider;
import net.ichigotake.common.app.FragmentFactory;
import net.ichigotake.common.app.OnPageChangeListener;
import net.ichigotake.common.app.WebBrowserTripper;
import net.ichigotake.common.os.RestoreBundle;
import net.ichigotake.common.widget.OnItemClickEventListener;

import circlebinder.Legacy;
import circlebinder.common.checklist.ChecklistColor;
import circlebinder.common.checklist.ChecklistPopupSelector;
import circlebinder.common.circle.CircleWebContainer;
import circlebinder.common.event.Circle;
import circlebinder.common.event.CircleBuilder;
import circlebinder.creation.BaseFragment;
import circlebinder.creation.R;
import circlebinder.creation.event.CircleTable;
import circlebinder.creation.checklist.UpdateChecklistListener;

public final class CircleDetailFragment extends BaseFragment
        implements UpdateChecklistListener, OnPageChangeListener, Legacy {

    public static FragmentFactory<CircleDetailFragment> factory(Circle circle) {
        return new CircleDetailFragmentFactory(circle);
    }

    private static class CircleDetailFragmentFactory implements FragmentFactory<CircleDetailFragment> {

        private final Circle circle;

        public CircleDetailFragmentFactory(Circle circle) {
            this.circle = circle;
        }

        @Override
        public CircleDetailFragment create() {
            return newInstance(circle);
        }
    }

    private static CircleDetailFragment newInstance(Circle circle) {
        CircleDetailFragment fragment = new CircleDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_CIRCLE, circle);
        fragment.setArguments(args);
        return fragment;
    }

    private static final String KEY_CIRCLE = "circle";
    private Circle circle;
    private CircleWebContainer webContainer;
    private View checklistView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        circle = new RestoreBundle(this, savedInstanceState).getParcelable(KEY_CIRCLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.circlebinder_circle_detail, parent, false);
        WebView webView = (WebView)view.findViewById(R.id.circlebinder_circle_detail_web_preview);
        webContainer = new CircleWebContainer(webView);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.circle_web, menu);
        MenuItem shareItem = menu.findItem(R.id.circlebinder_menu_circle_web_share);
        shareItem.setActionProvider(
                new ActionSendFilterActionProvider(getActivity(), webContainer.getCurrentUrl())
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.circlebinder_menu_circle_web_open_browser:
                new WebBrowserTripper(getActivity(), Uri.parse(webContainer.getCurrentUrl())).trip();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (circle.getLinks().isEmpty()) {
            webContainer.load(circle);
        } else {
            webContainer.loadUrl(circle.getLinks().get(0));
        }

        checklistView = getView().findViewById(R.id.circlebinder_circle_detail_checklist);
        final ChecklistPopupSelector checklistSelector =
                new ChecklistPopupSelector(getActivity(), checklistView);
        checklistView.setBackgroundResource(circle.getChecklistColor().getDrawableResource());
        checklistView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checklistSelector.isShowing()) {
                    checklistSelector.dismiss();
                } else {
                    checklistSelector.show();
                }
            }
        });
        checklistSelector.setOnItemClickListener(new OnItemClickEventListener<ChecklistColor>() {
            @Override
            public void onItemClick(ChecklistColor item) {
                update(item);
                checklistSelector.dismiss();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_CIRCLE, circle);
    }

    @Override
    public void update(ChecklistColor checklistColor) {
        checklistView.setBackgroundResource(circle.getChecklistColor().getDrawableResource());
        CircleTable.setChecklist(circle, checklistColor);
        circle = new CircleBuilder(circle)
                .setChecklistColor(checklistColor)
                .build();
    }

    @Override
    public void active() {
        restoreActionBar();
    }

    @Override
    public void inactive() {

    }

    private void restoreActionBar() {
        if (getActivity() != null) {
            getActivity().invalidateOptionsMenu();
            if (getActivity() instanceof OnCirclePageChangeListener) {
                ((OnCirclePageChangeListener)getActivity()).onCirclePageChanged(circle);
            }
        }
    }

    @Override
    public void onDestroy() {
        if (webContainer != null) {
            webContainer.onDestroy();
        }
        super.onDestroy();
    }

}
