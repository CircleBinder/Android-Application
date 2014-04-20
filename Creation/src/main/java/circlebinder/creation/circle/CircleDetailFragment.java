package circlebinder.creation.circle;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.internal.view.SupportMenuInflater;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import net.ichigotake.common.app.ActionSendFilterActionProvider;
import net.ichigotake.common.app.ActionViewActivityFactory;
import net.ichigotake.common.app.ActivityTripper;
import net.ichigotake.common.app.FragmentFactory;
import net.ichigotake.common.app.Pane;
import net.ichigotake.common.os.RestoreBundle;

import circlebinder.Legacy;
import circlebinder.common.checklist.ChecklistColor;
import circlebinder.common.circle.CircleWebContainer;
import circlebinder.common.event.Circle;
import circlebinder.common.event.CircleBuilder;
import circlebinder.common.event.CircleLink;
import circlebinder.common.view.ReloadWebViewActionProvider;
import circlebinder.creation.BaseFragment;
import circlebinder.creation.R;
import circlebinder.creation.event.CircleTable;
import circlebinder.creation.checklist.UpdateChecklistListener;

public final class CircleDetailFragment extends BaseFragment
        implements UpdateChecklistListener, Pane, Legacy {

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
    private ChecklistMenu checklistMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        circle = new RestoreBundle(this, savedInstanceState).getParcelable(KEY_CIRCLE);
        View view = inflater.inflate(R.layout.circlebinder_circle_detail, parent, false);
        WebView webView = (WebView)view.findViewById(R.id.circlebinder_circle_detail_web_preview);
        webContainer = new CircleWebContainer(webView);
        if (circle.getLinks().isEmpty()) {
            webContainer.load(circle);
        } else {
            webContainer.loadUrl(circle.getLinks().get(0));
        }

        checklistMenu = new ChecklistMenu(getSupportActivity(), this);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        new SupportMenuInflater(getSupportActivity());
        inflater.inflate(R.menu.circle_web, menu);
        inflater.inflate(R.menu.circle_links, menu);
        MenuItem shareItem = menu.findItem(R.id.circlebinder_menu_circle_web_share);
        MenuItemCompat.setActionProvider(
                shareItem,
                new ActionSendFilterActionProvider(getActivity(), webContainer.getCurrentUrl())
        );

        if (!circle.getLinks().isEmpty()) {
            for (CircleLink link : circle.getLinks().toList()) {
                MenuItem menuItem = menu.findItem(link.getType().getMenuItemId());
                menuItem.setVisible(true);
                MenuItemCompat.setActionProvider(menuItem, new ReloadWebViewActionProvider(
                                getActivity(),
                                webContainer,
                                link.getUri()
                        )
                );
            }
        } else {
            menu.findItem(R.id.circlebinder_menu_circle_links).setVisible(false);
        }
        checklistMenu.addMenu(menu, inflater, circle.getChecklistColor());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.circlebinder_menu_circle_web_open_browser:
                new ActivityTripper(
                        getActivity(),
                        new ActionViewActivityFactory(Uri.parse(webContainer.getCurrentUrl()))
                ).trip();
                return true;
        }
        return checklistMenu.onOptionsItemSelected(item)
                || super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void tap() {
        ActionBar actionBar =  getSupportActivity().getSupportActionBar();
        actionBar.setCustomView(R.layout.circlebinder_actionbar_circle_detail);
        actionBar.setDisplayShowCustomEnabled(true);
        CircleDetailViewHolder viewHolder = new CircleDetailViewHolder(actionBar.getCustomView());
        viewHolder.getName().setText(circle.getPenName() + "/" + circle.getName());
        viewHolder.getSpace().setText(circle.getSpace().getName());
        getSupportActivity().supportInvalidateOptionsMenu();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState == null) {
            outState = new Bundle();
        }
        outState.putParcelable(KEY_CIRCLE, circle);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void update(ChecklistColor checklistColor) {
        CircleTable.setChecklist(circle, checklistColor);
        circle = new CircleBuilder(circle)
                .setChecklistColor(checklistColor)
                .build();
    }

    @Override
    public void onDestroy() {
        if (webContainer != null) {
            webContainer.onDestroy();
        }
        super.onDestroy();
    }

}
