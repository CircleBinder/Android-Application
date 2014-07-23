package circlebinder.common.event;

import android.os.Parcel;
import android.os.Parcelable;

public final class AreaBuilder implements Parcelable {

    String name;
    String simpleName;

    public AreaBuilder() {}

    public Area build() {
        return new Area(this);
    }

    public AreaBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public AreaBuilder setSimpleName(String name) {
        this.simpleName = name;
        return this;
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

    private AreaBuilder(Parcel in) {
        this.name = in.readString();
        this.simpleName = in.readString();
    }

    public static Creator<AreaBuilder> CREATOR = new Creator<AreaBuilder>() {
        public AreaBuilder createFromParcel(Parcel source) {
            return new AreaBuilder(source);
        }

        public AreaBuilder[] newArray(int size) {
            return new AreaBuilder[size];
        }
    };
}
