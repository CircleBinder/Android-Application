package circlebinder.common.changelog;

import android.os.Parcel;
import android.os.Parcelable;

public final class ChangeLogFeed implements Parcelable {

    private final int versionCode;
    private final String versionName;
    private final ChangeLogFeedType type;
    private final PublishDate publishDate;
    private final String title;

    public ChangeLogFeed(int versionCode, String versionName, ChangeLogFeedType type, PublishDate publishDate, String title) {
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.type = type;
        this.publishDate = publishDate;
        this.title = title;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public ChangeLogFeedType getType() {
        return type;
    }

    public PublishDate getPublishDate() {
        return publishDate;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.versionCode);
        dest.writeString(this.versionName);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeParcelable(this.publishDate, 0);
        dest.writeString(this.title);
    }

    private ChangeLogFeed(Parcel in) {
        this.versionCode = in.readInt();
        this.versionName = in.readString();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : ChangeLogFeedType.values()[tmpType];
        this.publishDate = in.readParcelable(PublishDate.class.getClassLoader());
        this.title = in.readString();
    }

    public static Creator<ChangeLogFeed> CREATOR = new Creator<ChangeLogFeed>() {
        public ChangeLogFeed createFromParcel(Parcel source) {
            return new ChangeLogFeed(source);
        }

        public ChangeLogFeed[] newArray(int size) {
            return new ChangeLogFeed[size];
        }
    };
}
