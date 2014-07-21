package circlebinder.common.event;

import android.os.Parcel;
import android.os.Parcelable;

public final class TimestampBuilder implements Parcelable {

    long timestamp;
    String displayName;

    public Timestamp build() {
        return new Timestamp(this);
    }

    public TimestampBuilder setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public TimestampBuilder setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
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

    public TimestampBuilder() {
    }

    private TimestampBuilder(Parcel in) {
        this.timestamp = in.readLong();
        this.displayName = in.readString();
    }

    public static Creator<TimestampBuilder> CREATOR = new Creator<TimestampBuilder>() {
        public TimestampBuilder createFromParcel(Parcel source) {
            return new TimestampBuilder(source);
        }

        public TimestampBuilder[] newArray(int size) {
            return new TimestampBuilder[size];
        }
    };
}
