package circlebinder.android.app.service;

import android.os.Parcel;
import android.os.Parcelable;

public class IServiceCompleted implements Parcelable {

    private final String name;

    @Override
    public String toString() {
        return name;
    }

    public IServiceCompleted(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }

    private IServiceCompleted(Parcel in) {
        this.name = in.readString();
    }

    public static final Creator<IServiceCompleted> CREATOR = new Creator<IServiceCompleted>() {
        public IServiceCompleted createFromParcel(Parcel source) {
            return new IServiceCompleted(source);
        }

        public IServiceCompleted[] newArray(int size) {
            return new IServiceCompleted[size];
        }
    };
}
