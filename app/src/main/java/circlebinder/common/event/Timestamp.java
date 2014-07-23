package circlebinder.common.event;

import android.os.Parcel;
import android.os.Parcelable;

public class Timestamp implements Parcelable {

    private final long timestamp;
    private final String displayName;

    Timestamp(TimestampBuilder builder) {
        timestamp = builder.timestamp;
        displayName = builder.displayName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.timestamp);
        dest.writeString(this.displayName);
    }

    private Timestamp(Parcel in) {
        this.timestamp = in.readLong();
        this.displayName = in.readString();
    }

    public static Creator<Timestamp> CREATOR = new Creator<Timestamp>() {
        public Timestamp createFromParcel(Parcel source) {
            return new Timestamp(source);
        }

        public Timestamp[] newArray(int size) {
            return new Timestamp[size];
        }
    };
}
