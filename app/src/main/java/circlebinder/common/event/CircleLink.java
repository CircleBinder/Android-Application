package circlebinder.common.event;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class CircleLink implements Parcelable {

    private final Uri uri;
    private final int iconResource;
    private final CircleLinkType type;

    CircleLink(CircleLinkBuilder builder) {
        uri = builder.uri;
        iconResource = builder.iconResource;
        this.type = builder.type;
    }

    public Uri getUri() {
        return uri;
    }

    public int getIconResource() {
        return iconResource;
    }

    public CircleLinkType getType() {
        return type;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof CircleLink && uri.equals(((CircleLink)object).getUri());
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

    private CircleLink(Parcel in) {
        this.uri = in.readParcelable(Uri.class.getClassLoader());
        this.iconResource = in.readInt();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : CircleLinkType.values()[tmpType];
    }

    public static Creator<CircleLink> CREATOR = new Creator<CircleLink>() {
        public CircleLink createFromParcel(Parcel source) {
            return new CircleLink(source);
        }

        public CircleLink[] newArray(int size) {
            return new CircleLink[size];
        }
    };
}
