package circlebinder.common.card;

import android.content.Context;
import android.content.Intent;

public interface HomeCard {

    CharSequence getLabel();

    CharSequence getCaption();

    int getBackgroundResource();

    Intent createTransitIntent(Context context);

}
