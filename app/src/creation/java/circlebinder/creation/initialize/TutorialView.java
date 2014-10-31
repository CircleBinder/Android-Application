package circlebinder.creation.initialize;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.dmitriy.tarasov.android.intents.IntentUtils;

import net.ichigotake.common.app.ActivityTripper;
import net.ichigotake.common.app.OnClickToTrip;

import circlebinder.R;

/**
 * 初期化をする
 */
public final class TutorialView extends CardView {

    public TutorialView(Context context) {
        super(context);
    }

    public TutorialView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TutorialView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.creation_fragment_database_initialize_content, this, true);
        TextView twitterHashTagView = (TextView) view.findViewById(
                R.id.common_fragment_contact_twitter_official_hash_tag
        );
        String twitterHashTagUrl = getContext().getString(R.string.common_twitter_official_hash_tag_url);
        twitterHashTagView.setText(Html.fromHtml(
                "<a href=\"" + twitterHashTagUrl + "\">" +
                        getContext().getString(R.string.common_twitter_official_hash_tag_name) +
                        "</a>"
        ));
        twitterHashTagView.setOnClickListener(new OnClickToTrip(
                new ActivityTripper(
                        getContext(),
                        IntentUtils.openLink(twitterHashTagUrl))
        ));

        TextView twitterScreenNameView = (TextView) view.findViewById(
                R.id.common_fragment_contact_twitter_official_account_screen_name
        );
        String twitterScreenNameUrl = getContext().getString(R.string.common_twitter_official_account_url);
        twitterScreenNameView.setText(Html.fromHtml(
                "<a href=\"" + twitterScreenNameUrl + "\">" +
                        getContext().getString(R.string.common_twitter_official_account_screen_name) +
                        "</a>"
        ));
        twitterScreenNameView.setOnClickListener(new OnClickToTrip(
                new ActivityTripper(
                        getContext(),
                        IntentUtils.openLink(twitterScreenNameUrl))
        ));

    }


}
