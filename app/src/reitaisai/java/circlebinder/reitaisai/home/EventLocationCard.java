package circlebinder.reitaisai.home;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;

import circlebinder.R;
import circlebinder.common.app.phone.WebViewActivity;
import circlebinder.common.card.HomeCard;

public final class EventLocationCard implements HomeCard, android.os.Parcelable {

    private final String label;
    private final String caption;

    public EventLocationCard(Context context) {
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
        return R.color.app_color_accent;
    }

    @Override
    public Intent createTransitIntent(Context context) {
        return WebViewActivity.createIntent(context, context.getString(R.string.app_event_location));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.label);
        dest.writeString(this.caption);
    }

    private EventLocationCard(Parcel in) {
        this.label = in.readString();
        this.caption = in.readString();
    }

    public static final Creator<EventLocationCard> CREATOR = new Creator<EventLocationCard>() {
        public EventLocationCard createFromParcel(Parcel source) {
            return new EventLocationCard(source);
        }

        public EventLocationCard[] newArray(int size) {
            return new EventLocationCard[size];
        }
    };
}
