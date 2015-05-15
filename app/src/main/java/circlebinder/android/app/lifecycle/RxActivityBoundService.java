package circlebinder.android.app.lifecycle;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import circlebinder.android.app.Logger;
import circlebinder.android.app.service.BackgroundService;
import circlebinder.android.app.service.IBackgroundServiceIF;
import circlebinder.android.app.service.IBackgroundServiceListener;
import circlebinder.android.app.service.IServiceCompleted;
import circlebinder.android.app.service.IServiceFutureFactory;
import circlebinder.android.app.service.BackgroundServiceCommand;

public abstract class RxActivityBoundService extends RxActivity {

    private final String TAG = RxActivityBoundService.class.getSimpleName();
    private IBackgroundServiceIF serviceIF;

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Logger.debug(TAG, "onServiceConnected");
            serviceIF = IBackgroundServiceIF.Stub.asInterface(service);
            registerBServiceListener();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Logger.debug(TAG, "onServiceDisconnected");
        }

    };

    private final IBackgroundServiceListener serviceListener = new IBackgroundServiceListener.Stub() {
        @Override
        public void onEvent(IServiceCompleted event) throws RemoteException {
            onBackgroundServiceCompleted(event);
        }

    };

    abstract protected void onBackgroundServiceCompleted(IServiceCompleted event);

    private void registerBServiceListener() {
        if (serviceIF == null) {
            Logger.debug(TAG, "registerBServiceListener bindService before");
            bindService(BackgroundService.createIntent(this), serviceConnection, Context.BIND_AUTO_CREATE);
            Logger.debug(TAG, "registerBServiceListener bindService after");
            return;
        }
        try {
            Logger.debug(TAG, "registerBServiceListener registerListener before");
            serviceIF.registerListener(serviceListener);
            Logger.debug(TAG, "registerBServiceListener registerListener after");
            sendCommand(createCommand());
        } catch (RemoteException e) {
            Logger.error(TAG, e);
        }
    }

    private void unregisterBServiceListener() {
        try {
            serviceIF.unregisterListener(serviceListener);
        } catch (RemoteException e) {
            Logger.error(TAG, e);
        }
        try {
            unbindService(serviceConnection);
            serviceIF = null;
        } catch (IllegalArgumentException e) {
            Logger.error(TAG, e);
        }
    }

    abstract protected BackgroundServiceCommand createCommand();

    private void sendCommand(BackgroundServiceCommand command) {
        if (serviceIF == null) {
            Logger.debug(TAG, "sendCommand(serviceIF=null)");
            return;
        }
        try {
            Logger.debug(TAG, "sendCommand before");
            serviceIF.sendCommand(new IServiceFutureFactory(command));
            Logger.debug(TAG, "sendCommand after");
        } catch (RemoteException e) {
            Logger.error(TAG, e);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBServiceListener();
    }

    @Override
    protected void onStop() {
        unregisterBServiceListener();
        super.onStop();
    }

}
