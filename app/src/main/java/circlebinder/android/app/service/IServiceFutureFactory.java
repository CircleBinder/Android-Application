package circlebinder.android.app.service;

import android.os.Parcel;
import android.os.Parcelable;

import rx.Observable;

public class IServiceFutureFactory implements Parcelable {

    private final BackgroundServiceCommand factory;

    public IServiceFutureFactory(BackgroundServiceCommand factory) {
        this.factory = factory;
    }

    public Observable<IServiceCompleted> createFuture() {
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

    private IServiceFutureFactory(Parcel in) {
        this.factory = (BackgroundServiceCommand) in.readSerializable();
    }

    public static final Parcelable.Creator<IServiceFutureFactory> CREATOR = new Parcelable.Creator<IServiceFutureFactory>() {
        public IServiceFutureFactory createFromParcel(Parcel source) {
            return new IServiceFutureFactory(source);
        }

        public IServiceFutureFactory[] newArray(int size) {
            return new IServiceFutureFactory[size];
        }
    };

}
