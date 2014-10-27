package circlebinder.creation.home;

import android.content.Context;
import android.content.Intent;

import com.dmitriy.tarasov.android.intents.IntentUtils;

import circlebinder.R;
import circlebinder.common.card.HomeCard;

public final class CreationOfficialTwitterCard implements HomeCard {

    private final CharSequence label;
    private final CharSequence caption;

    public CreationOfficialTwitterCard(Context context) {
        this.label = context.getString(R.string.app_creation_twitter_label);
        this.caption = context.getString(R.string.app_creation_twitter_account_label);
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
        return IntentUtils.openLink(context.getString(R.string.app_creation_twitter_account_url));
    }
}
