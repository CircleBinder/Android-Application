package circlebinder.android.app.service;

import android.os.Parcel;
import android.os.Parcelable;

import rx.Observable;

public class IBackgroundTaskFactory implements Parcelable {

    private final BackgroundServiceCommand futureFactory;

    public IBackgroundTaskFactory(BackgroundServiceCommand futureFactory) {
        this.futureFactory = futureFactory;
    }

    Observable<IServiceCompleted> createFuture() {
        return futureFactory.createObservable();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.futureFactory);
    }

    private IBackgroundTaskFactory(Parcel in) {
        this.futureFactory = (BackgroundServiceCommand) in.readSerializable();
    }

    public static final Creator<IBackgroundTaskFactory> CREATOR = new Creator<IBackgroundTaskFactory>() {
        public IBackgroundTaskFactory createFromParcel(Parcel source) {
            return new IBackgroundTaskFactory(source);
        }

        public IBackgroundTaskFactory[] newArray(int size) {
            return new IBackgroundTaskFactory[size];
        }
    };
}
