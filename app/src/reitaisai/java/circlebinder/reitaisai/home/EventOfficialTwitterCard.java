package circlebinder.reitaisai.home;

import android.content.Context;
import android.content.Intent;

import com.dmitriy.tarasov.android.intents.IntentUtils;

import circlebinder.R;
import circlebinder.common.card.HomeCard;

public final class EventOfficialTwitterCard implements HomeCard {

    private final String label;
    private final String caption;

    public EventOfficialTwitterCard(Context context) {
        this.label = context.getString(R.string.app_event_twitter_label);
        this.caption = context.getString(R.string.app_event_twitter_account_label);
    }

    @Override
    public CharSequence getLabel() {
        return label;
    }

    @Override
    public CharSequence getCaption() {
        return caption;
    }

    @Override
    public int getBackgroundResource() {
        return R.color.common_card_twitter_background;
    }

    @Override
    public Intent createTransitIntent(Context context) {
        return IntentUtils.openLink(context.getString(R.string.app_event_twitter_account_url));
    }

}
