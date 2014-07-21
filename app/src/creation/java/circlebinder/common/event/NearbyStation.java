package circlebinder.common.event;

import android.os.Parcel;
import android.os.Parcelable;

public class NearbyStation implements Parcelable {

    private final String display;

    NearbyStation(NearbyStationBuilder builder) {
        display = builder.display;
    }

    public String getDisplay() {
        return display;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.display);
    }

    private NearbyStation(Parcel in) {
        this.display = in.readString();
    }

    public static Creator<NearbyStation> CREATOR = new Creator<NearbyStation>() {
        public NearbyStation createFromParcel(Parcel source) {
            return new NearbyStation(source);
        }

        public NearbyStation[] newArray(int size) {
            return new NearbyStation[size];
        }
    };
}
