package circlebinder.android.app.service;

import android.os.Parcel;
import android.os.Parcelable;

import rx.functions.Action1;

public class IServiceCompleted implements Parcelable {

    private final String name;
    private final Throwable error;

    @Override
    public String toString() {
        return name;
    }

    public IServiceCompleted(String name) {
        this.name = name;
        this.error = null;
    }

    public IServiceCompleted(String name, Throwable error) {
        this.name = name;
        this.error = error;
    }

    public void either(Action1<Throwable> error, Action1<String> right) {
        if (error != null) {
            error.call(this.error);
            return;
        }
        right.call(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeSerializable(this.error);
    }

    private IServiceCompleted(Parcel in) {
        this.name = in.readString();
        this.error = (Throwable) in.readSerializable();
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
