package circlebinder.creation.system;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmitriy.tarasov.android.intents.IntentUtils;

import net.ichigotake.common.app.OnClickToTrip;
import net.ichigotake.common.widget.TextViewUtil;

import circlebinder.common.Legacy;
import circlebinder.common.app.ContactTripper;
import circlebinder.common.app.BaseFragment;
import circlebinder.R;

public final class ContactFragment extends BaseFragment implements Legacy {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restoreActionBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact, parent, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();

        view.findViewById(R.id.fragment_contact_send).setOnClickListener(
                new OnClickToTrip(new ContactTripper(getActivity(), getString(R.string.app_name)))
        );

        TextView twitterHashTagView = (TextView) view.findViewById(
                R.id.twitter_official_hash_tag_name
        );
        String twitterHashTagUrl = getString(R.string.common_twitter_official_hash_tag_url);
        twitterHashTagView.setText(getString(R.string.common_twitter_official_hash_tag_name));
        TextViewUtil.hyperLinkDecoration(twitterHashTagView, twitterHashTagUrl);
        twitterHashTagView.setOnClickListener(
                OnClickToTrip.activityTrip(getActivity(), IntentUtils.openLink(twitterHashTagUrl))
        );

        TextView twitterScreenNameView = (TextView) view.findViewById(
                R.id.twitter_official_account_screen_name
        );
        String twitterScreenNameUrl = getString(R.string.common_twitter_official_account_url);
        twitterScreenNameView.setText(getString(R.string.common_twitter_official_account_screen_name));
        TextViewUtil.hyperLinkDecoration(twitterScreenNameView, twitterScreenNameUrl);
        twitterScreenNameView.setOnClickListener(
                OnClickToTrip.activityTrip(getActivity(), IntentUtils.openLink(twitterScreenNameUrl))
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        restoreActionBar();
    }

    private void restoreActionBar() {
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.common_send_feedback_wish_me_luck);
    }

}
