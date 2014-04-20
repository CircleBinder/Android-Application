package circlebinder.creation.system;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.ichigotake.common.app.ActionViewActivityFactory;
import net.ichigotake.common.app.ActivityTripper;
import net.ichigotake.common.app.FragmentFactory;
import net.ichigotake.common.app.OnClickToTrip;

import circlebinder.Legacy;
import circlebinder.common.app.ContactTripper;
import circlebinder.common.app.FragmentTripper;
import circlebinder.creation.BaseFragment;
import circlebinder.creation.R;

public final class ContactFragment extends BaseFragment implements Legacy {

    public static FragmentTripper tripper(FragmentManager fragmentManager) {
        return new FragmentTripper(fragmentManager, factory());
    }

    public static FragmentFactory<ContactFragment> factory() {
        return new FragmentFactory<ContactFragment>() {
            @Override
            public ContactFragment create() {
                return newInstance();
            }
        };
    }

    private static ContactFragment newInstance() {
        return new ContactFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restoreActionBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.circlebinder_fragment_contact, parent, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();

        view.findViewById(R.id.circlebinder_fragment_contact_send).setOnClickListener(
                new OnClickToTrip(new ContactTripper(getActivity(), getString(R.string.app_name)))
        );

        TextView twitterHashTagView = (TextView) view.findViewById(
                R.id.circlebinder_twitter_official_hash_tag_name
        );
        String twitterHashTagUrl = getString(R.string.circlebinder_twitter_official_hash_tag_url);
        twitterHashTagView.setText(Html.fromHtml(
                "<a href=\"" + twitterHashTagUrl + "\">" +
                        getString(R.string.circlebinder_twitter_official_hash_tag_name) +
                        "</a>"
        ));
        twitterHashTagView.setOnClickListener(new OnClickToTrip(
                new ActivityTripper(
                        getActivity(),
                        new ActionViewActivityFactory(Uri.parse(twitterHashTagUrl)))
        ));

        TextView twitterScreenNameView = (TextView) view.findViewById(
                R.id.circlebinder_twitter_official_account_screen_name
        );
        String twitterScreenNameUrl = getString(R.string.circlebinder_twitter_official_account_url);
        twitterScreenNameView.setText(Html.fromHtml(
                "<a href=\"" + twitterScreenNameUrl + "\">" +
                        getString(R.string.circlebinder_twitter_official_account_screen_name) +
                        "</a>"
        ));
        twitterScreenNameView.setOnClickListener(new OnClickToTrip(
                new ActivityTripper(
                        getActivity(),
                        new ActionViewActivityFactory(Uri.parse(twitterScreenNameUrl)))
        ));
    }

    @Override
    public void onResume() {
        super.onResume();
        restoreActionBar();
    }

    private void restoreActionBar() {
        ActionBar actionBar = getSupportActivity().getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.circlebinder_navigation_wish_me_luck);
    }

}
