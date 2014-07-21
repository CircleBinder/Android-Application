package circlebinder.common.event;

import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable {

    private final String displayName;
    private final String link;

    Location(LocationBuilder builder) {
        displayName = builder.displayName;
        link = builder.link;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getLink() {
        return link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.displayName);
        dest.writeString(this.link);
    }

    private Location(Parcel in) {
        this.displayName = in.readString();
        this.link = in.readString();
    }

    public static Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

}
