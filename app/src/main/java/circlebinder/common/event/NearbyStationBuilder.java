package circlebinder.common.event;

import android.os.Parcel;
import android.os.Parcelable;

public final class NearbyStationBuilder implements Parcelable {

    String display;

    public NearbyStation build() {
        return new NearbyStation(this);
    }

    public NearbyStationBuilder setDisplay(String display) {
        this.display = display;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.display);
    }

    public NearbyStationBuilder() {
    }

    private NearbyStationBuilder(Parcel in) {
        this.display = in.readString();
    }

    public static Creator<NearbyStationBuilder> CREATOR = new Creator<NearbyStationBuilder>() {
        public NearbyStationBuilder createFromParcel(Parcel source) {
            return new NearbyStationBuilder(source);
        }

        public NearbyStationBuilder[] newArray(int size) {
            return new NearbyStationBuilder[size];
        }
    };
}
