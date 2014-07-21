package circlebinder.common.event;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public final class CircleLinkBuilder implements Parcelable {

    Uri uri;
    int iconResource;
    CircleLinkType type;

    public CircleLinkBuilder() {
    }

    public CircleLink build() {
        return new CircleLink(this);
    }

    public CircleLinkBuilder setUri(Uri uri) {
        this.uri = uri;
        return this;
    }

    public CircleLinkBuilder setIcon(int iconResource) {
        this.iconResource = iconResource;
        return this;
    }

    public CircleLinkBuilder setType(CircleLinkType type) {
        this.type = type;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.uri, 0);
        dest.writeInt(this.iconResource);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
    }

    private CircleLinkBuilder(Parcel in) {
        this.uri = in.readParcelable(Uri.class.getClassLoader());
        this.iconResource = in.readInt();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : CircleLinkType.values()[tmpType];
    }

    public static Creator<CircleLinkBuilder> CREATOR = new Creator<CircleLinkBuilder>() {
        public CircleLinkBuilder createFromParcel(Parcel source) {
            return new CircleLinkBuilder(source);
        }

        public CircleLinkBuilder[] newArray(int size) {
            return new CircleLinkBuilder[size];
        }
    };
}
