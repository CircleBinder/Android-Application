package circlebinder.common.event;

import android.os.Parcel;
import android.os.Parcelable;

public final class LocationBuilder implements Parcelable {

    String displayName;
    String link;

    public Location build() {
        return new Location(this);
    }

    public LocationBuilder setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public LocationBuilder setLink(String link) {
        this.link = link;
        return this;
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

    public LocationBuilder() {
    }

    private LocationBuilder(Parcel in) {
        this.displayName = in.readString();
        this.link = in.readString();
    }

    public static Creator<LocationBuilder> CREATOR = new Creator<LocationBuilder>() {
        public LocationBuilder createFromParcel(Parcel source) {
            return new LocationBuilder(source);
        }

        public LocationBuilder[] newArray(int size) {
            return new LocationBuilder[size];
        }
    };
}
