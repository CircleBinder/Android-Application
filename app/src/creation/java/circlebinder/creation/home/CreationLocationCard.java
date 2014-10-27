package circlebinder.creation.home;

import android.content.Context;
import android.content.Intent;

import com.dmitriy.tarasov.android.intents.IntentUtils;

import net.ichigotake.common.content.RawResources;

import java.io.IOException;

import circlebinder.R;
import circlebinder.common.card.HomeCard;

public final class CreationLocationCard implements HomeCard{

    private final CharSequence label;
    private final CharSequence caption;

    public CreationLocationCard(Context context) {
        this.label = context.getString(R.string.app_event_name_short);
        this.caption = context.getString(R.string.common_open_event_location);
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
        return R.color.app_theme_base;
    }

    @Override
    public Intent createTransitIntent(Context context) {
        try {
            String eventMapGeoUrl = new RawResources(context.getResources())
                    .getText(R.raw.event_map_geo_url).get(0);
            return IntentUtils.openLink(eventMapGeoUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
