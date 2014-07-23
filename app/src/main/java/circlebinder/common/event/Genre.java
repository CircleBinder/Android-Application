package circlebinder.common.event;

import android.os.Parcel;

public class Genre implements android.os.Parcelable {

    private final int id;
    private final String name;

    Genre(GenreBuilder builder) {
        id = builder.id;
        name = builder.name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
    }

    private Genre(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
    }

    public static Creator<Genre> CREATOR = new Creator<Genre>() {
        public Genre createFromParcel(Parcel source) {
            return new Genre(source);
        }

        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };
}
