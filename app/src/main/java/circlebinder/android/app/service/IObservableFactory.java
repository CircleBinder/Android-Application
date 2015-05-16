package circlebinder.android.app.service;

import android.os.Parcel;
import android.os.Parcelable;

import rx.Observable;

public class IObservableFactory implements Parcelable {

    private final BackgroundServiceCommand factory;

    public IObservableFactory(BackgroundServiceCommand factory) {
        this.factory = factory;
    }

    public Observable<IServiceCompleted> createObservable() {
        return factory.createObservable();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.factory);
    }

    private IObservableFactory(Parcel in) {
        this.factory = (BackgroundServiceCommand) in.readSerializable();
    }

    public static final Parcelable.Creator<IObservableFactory> CREATOR = new Parcelable.Creator<IObservableFactory>() {
        public IObservableFactory createFromParcel(Parcel source) {
            return new IObservableFactory(source);
        }

        public IObservableFactory[] newArray(int size) {
            return new IObservableFactory[size];
        }
    };

}
