package circlebinder.android.app.service;

import java.io.Serializable;

import rx.Observable;

public interface BackgroundServiceCommand extends Serializable {

    Observable<IServiceCompleted> createObservable();

}
