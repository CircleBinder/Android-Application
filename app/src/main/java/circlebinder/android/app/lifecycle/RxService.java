package circlebinder.android.app.lifecycle;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import rx.Observable;
import rx.android.lifecycle.LifecycleEvent;
import rx.subjects.BehaviorSubject;

public abstract class RxService extends Service {

    private final BehaviorSubject<LifecycleEvent> lifecycleSubject = BehaviorSubject.create();

    protected Observable<LifecycleEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        lifecycleSubject.onNext(LifecycleEvent.CREATE);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        lifecycleSubject.onNext(LifecycleEvent.DESTROY);
        super.onDestroy();
    }

}
