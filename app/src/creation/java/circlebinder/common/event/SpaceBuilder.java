package circlebinder.common.event;

import android.os.Parcel;
import android.os.Parcelable;

public final class SpaceBuilder implements Parcelable {

    String name;
    String simpleName;
    long blockId;
    String blockName;
    int spaceNo;
    String spaceNoSub;

    public Space build() {
        return new Space(this);
    }

    public SpaceBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public SpaceBuilder setSimpleName(String simpleName) {
        this.simpleName = simpleName;
        return this;
    }

    public SpaceBuilder setBlockName(String blockName) {
        this.blockName = blockName;
        return this;
    }

    public SpaceBuilder setNo(int spaceNo) {
        this.spaceNo = spaceNo;
        return this;
    }

    public SpaceBuilder setNoSub(String noSub) {
        this.spaceNoSub = noSub;
        return this;
    }

    public SpaceBuilder setBlockId(long blockId) {
        this.blockId = blockId;
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
        dest.writeString(this.blockName);
        dest.writeInt(this.spaceNo);
        dest.writeString(this.spaceNoSub);
    }

    public SpaceBuilder() {
    }

    private SpaceBuilder(Parcel in) {
        this.name = in.readString();
        this.simpleName = in.readString();
        this.blockName = in.readString();
        this.spaceNo = in.readInt();
        this.spaceNoSub = in.readString();
    }

    public static Parcelable.Creator<SpaceBuilder> CREATOR = new Parcelable.Creator<SpaceBuilder>() {
        public SpaceBuilder createFromParcel(Parcel source) {
            return new SpaceBuilder(source);
        }

        public SpaceBuilder[] newArray(int size) {
            return new SpaceBuilder[size];
        }
    };

    public void clear() {
        this.name = null;
        this.simpleName = null;
        this.blockName = null;
        this.spaceNo = 0;
        this.spaceNoSub = null;
    }
}
