package circlebinder.common.system;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dmitriy.tarasov.android.intents.IntentUtils;

import net.ichigotake.common.app.OnClickToTrip;
import net.ichigotake.common.util.Finders;
import net.ichigotake.common.util.ViewFinder;
import net.ichigotake.common.widget.TextViewUtil;

import circlebinder.R;
import circlebinder.common.app.ContactTripper;

public class ContactView extends LinearLayout {

    public ContactView(Context context) {
        super(context);
    }

    public ContactView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContactView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("unused") // Public API
    public ContactView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.common_view_contact, this, true);
        ViewFinder finder = Finders.from(view);
        finder.find(R.id.common_view_contact_send).setOnClickListener(
                new OnClickToTrip(new ContactTripper(getContext(), getContext().getString(R.string.app_name)))
        );

        TextView twitterHashTagView = finder.find(R.id.common_view_contact_twitter_official_hash_tag);
        String twitterHashTagUrl = getContext().getString(R.string.common_twitter_official_hash_tag_url);
        twitterHashTagView.setText(getContext().getString(R.string.common_twitter_official_hash_tag_name));
        TextViewUtil.hyperLinkDecoration(twitterHashTagView, twitterHashTagUrl);
        twitterHashTagView.setOnClickListener(
                OnClickToTrip.activityTrip(getContext(), IntentUtils.openLink(twitterHashTagUrl))
        );

        TextView twitterScreenNameView = finder
                .find(R.id.common_view_contact_twitter_official_account_screen_name);
        String twitterScreenNameUrl = getContext().getString(R.string.common_twitter_official_account_url);
        twitterScreenNameView.setText(getContext().getString(R.string.common_twitter_official_account_screen_name));
        TextViewUtil.hyperLinkDecoration(twitterScreenNameView, twitterScreenNameUrl);
        twitterScreenNameView.setOnClickListener(
                OnClickToTrip.activityTrip(getContext(), IntentUtils.openLink(twitterScreenNameUrl))
        );
    }

}
