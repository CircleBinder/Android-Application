package circlebinder.common.event;

import android.os.Parcel;
import android.os.Parcelable;

public class Block implements Parcelable {
    private final long id;
    private final Area area;
    private final String name;
    private final EventBlockType type;

    Block(BlockBuilder builder) {
        id = builder.id;
        area = builder.area;
        name = builder.name;
        type = builder.type;
    }

    public long getId() {
        return id;
    }

    public Area getArea() {
        return area;
    }

    public String getName() {
        return name;
    }

    public EventBlockType getType() {
        return type;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Block && id == getId();
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
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
    }

    private Block(Parcel in) {
        this.id = in.readLong();
        this.area = in.readParcelable(Area.class.getClassLoader());
        this.name = in.readString();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : EventBlockType.values()[tmpType];
    }

    public static final Creator<Block> CREATOR = new Creator<Block>() {
        public Block createFromParcel(Parcel source) {
            return new Block(source);
        }

        public Block[] newArray(int size) {
            return new Block[size];
        }
    };

}
