package circlebinder.common.event;

import android.os.Parcel;
import android.os.Parcelable;

public final class EventDayBuilder implements Parcelable {

    String name;

    public EventDay build() {
        return new EventDay(this);
    }

    public EventDayBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }

    public EventDayBuilder() {
    }

    private EventDayBuilder(Parcel in) {
        this.name = in.readString();
    }

    public static Creator<EventDayBuilder> CREATOR = new Creator<EventDayBuilder>() {
        public EventDayBuilder createFromParcel(Parcel source) {
            return new EventDayBuilder(source);
        }

        public EventDayBuilder[] newArray(int size) {
            return new EventDayBuilder[size];
        }
    };
}
