package circlebinder.common.event;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {

    private final String name;
    private final Location location;
    private final EventDay day;

    Event(EventBuilder builder) {
        name = builder.name;
        location = builder.location;
        day = builder.day;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public EventDay getDay() {
        return day;
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

    private Event(Parcel in) {
        this.name = in.readString();
        this.location = in.readParcelable(Location.class.getClassLoader());
        this.day = in.readParcelable(EventDay.class.getClassLoader());
    }

    public static Creator<Event> CREATOR = new Creator<Event>() {
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
