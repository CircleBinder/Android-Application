package circlebinder.common.event;

import android.os.Parcel;
import android.os.Parcelable;

public final class EventBuilder implements Parcelable {

    String name;
    Location location;
    EventDay day;

    public Event build() {
        return new Event(this);
    }

    public EventBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public EventBuilder setLocation(Location location) {
        this.location = location;
        return this;
    }

    public EventBuilder setDay(EventDay day) {
        this.day = day;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeParcelable(this.location, 0);
        dest.writeParcelable(this.day, 0);
    }

    public EventBuilder() {
    }

    private EventBuilder(Parcel in) {
        this.name = in.readString();
        this.location = in.readParcelable(((Object) location).getClass().getClassLoader());
        this.day = in.readParcelable(EventDay.class.getClassLoader());
    }

    public static Creator<EventBuilder> CREATOR = new Creator<EventBuilder>() {
        public EventBuilder createFromParcel(Parcel source) {
            return new EventBuilder(source);
        }

        public EventBuilder[] newArray(int size) {
            return new EventBuilder[size];
        }
    };
}
