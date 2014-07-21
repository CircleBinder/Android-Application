package circlebinder.common.event;

import android.os.Parcel;
import android.os.Parcelable;

public class Area implements Parcelable {

    private final String name;
    private final String simpleName;

    Area(AreaBuilder builder) {
        name = builder.name;
        simpleName = builder.simpleName;
    }

    public String getName() {
        return name;
    }

    public String getSimpleName() {
        return simpleName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.simpleName);
    }

    private Area(Parcel in) {
        this.name = in.readString();
        this.simpleName = in.readString();
    }

    public static Parcelable.Creator<Area> CREATOR = new Parcelable.Creator<Area>() {
        public Area createFromParcel(Parcel source) {
            return new Area(source);
        }

        public Area[] newArray(int size) {
            return new Area[size];
        }
    };
}
