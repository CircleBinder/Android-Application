package circlebinder.common.event;

import android.os.Parcel;
import android.os.Parcelable;

public final class BlockBuilder implements Parcelable {

    long id;
    Area area;
    String name;

    public BlockBuilder() {}

    public Block build() {
        return new Block(this);
    }

    public BlockBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public BlockBuilder setArea(Area area) {
        this.area = area;
        return this;
    }

    public BlockBuilder setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeParcelable(this.area, 0);
        dest.writeString(this.name);
    }

    private BlockBuilder(Parcel in) {
        this.id = in.readLong();
        this.area = in.readParcelable(Area.class.getClassLoader());
        this.name = in.readString();
    }

    public static Creator<BlockBuilder> CREATOR = new Creator<BlockBuilder>() {
        public BlockBuilder createFromParcel(Parcel source) {
            return new BlockBuilder(source);
        }

        public BlockBuilder[] newArray(int size) {
            return new BlockBuilder[size];
        }
    };
}
