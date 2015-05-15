package circlebinder.android.app.service;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import circlebinder.android.app.Logger;
import circlebinder.android.app.lifecycle.RxService;
import rx.Observable;
import rx.Subscriber;
import rx.android.lifecycle.LifecycleEvent;
import rx.android.lifecycle.LifecycleObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Bind {@link android.app.Service} to {@link android.app.Activity}.
 *
 * ref: https://github.com/youten/BService
 */
public class BackgroundService extends RxService {

    public static Intent createIntent(Context context) {
        return new Intent(context, BackgroundService.class);
    }

    private final String TAG = BackgroundService.class.getSimpleName();
    private final RemoteCallbackList<IBackgroundServiceListener> callbackList = new RemoteCallbackList<>();

    private final IBackgroundServiceIF.Stub stub = new IBackgroundServiceIF.Stub() {
        @Override
        public void registerListener(IBackgroundServiceListener listener) throws RemoteException {
            callbackList.register(listener);
        }

        @Override
        public void unregisterListener(IBackgroundServiceListener listener) throws RemoteException {
            callbackList.unregister(listener);
        }

        @Override
        public void sendCommand(IServiceFutureFactory factory) throws RemoteException {
            if (factory == null) {
                Logger.debug(TAG, "sendCommand(factory=null)");
                return;
            }
            Logger.debug(TAG, String.format("sendCommand(factory=%s)", factory));
            LifecycleObservable
                    .bindUntilLifecycleEvent(lifecycle(), createObservable(factory), LifecycleEvent.STOP)
                    .subscribe(new Callback());
        }
    };

    private Observable<IServiceCompleted> createObservable(IServiceFutureFactory factory) {
        return factory.createFuture()
                .observeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.debug(TAG, "onStartCommand");
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    public class Callback extends Subscriber<IServiceCompleted> {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(IServiceCompleted event) {
            synchronized (callbackList) {
                int listenersSize = callbackList.beginBroadcast();
                if (listenersSize <= 0) {
                    callbackList.finishBroadcast();
                    return;
                }
                for (int i=0; i<listenersSize; i++) {
                    try {
                        callbackList.getBroadcastItem(i).onEvent(event);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                callbackList.finishBroadcast();
            }
        }
    }

}
