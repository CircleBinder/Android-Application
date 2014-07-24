package circlebinder.creation.initialize;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import net.ichigotake.common.os.RunnableAsyncTask;

import circlebinder.common.Legacy;

public final class DatabaseInitializeService extends Service implements Legacy {

    private final RemoteCallbackList<IInitializeServiceCallback> callbacks =
            new RemoteCallbackList<IInitializeServiceCallback>();

    private IInitializeBindService.Stub stub = new IInitializeBindService.Stub() {

        @Override
        public void setObserver(IInitializeServiceCallback callback) throws RemoteException {
            callbacks.register(callback);
        }

        @Override
        public void removeObserver(IInitializeServiceCallback callback) throws RemoteException {
            callbacks.unregister(callback);
        }

        @Override
        public void AsyncStart() throws RemoteException {
            RunnableAsyncTask.execute(new CreationDatabaseInitialize(getApplicationContext()) {
                @Override
                void finished() {
                    callback();
                }
            });
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    private void callback() {
        int n = callbacks.beginBroadcast();
        for (int i=0; i<n; i++) {
            try {
                callbacks.getBroadcastItem(i).initializeCompleted();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        callbacks.finishBroadcast();
    }

}
