package circlebinder.creation.app.phone;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.Fragment;

import net.ichigotake.common.app.ActivityFactory;
import net.ichigotake.common.app.ActivityTripper;

import circlebinder.creation.BaseActivity;
import circlebinder.creation.R;
import circlebinder.creation.initialize.DatabaseInitializeService;
import circlebinder.creation.initialize.IInitializeBindService;
import circlebinder.creation.initialize.IInitializeFragment;
import circlebinder.creation.initialize.IInitializeServiceCallback;

public final class DatabaseInitializeActivity extends BaseActivity {

    public static ActivityFactory from() {
        return new DatabaseInitializeActivityFactory();
    }

    public static ActivityTripper tripper(Context context) {
        return new ActivityTripper(context, from());
    }

    private static class DatabaseInitializeActivityFactory implements ActivityFactory {

        @Override
        public Intent create(Context context) {
            return new Intent(context, DatabaseInitializeActivity.class);
        }
    }


    private final String FRAGMENT_TAG_INITIALIZE = "initialize";
    private boolean serviceBind;
    private IInitializeBindService mService;
    private IInitializeServiceCallback callback = new IInitializeServiceCallback.Stub() {
        @Override
        public void initializeCompleted() throws RemoteException {
            Fragment callback = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG_INITIALIZE);
            if (callback instanceof IInitializeServiceCallback) {
                ((IInitializeServiceCallback)callback).initializeCompleted();
            } else {
                throw new IllegalStateException("not implements");
            }
        }
    };
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IInitializeBindService.Stub.asInterface(service);
            try {
                mService.setObserver(callback);
                mService.AsyncStart();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circlebinder_activity_basic);
        serviceBind = true;
        bindService(new Intent(this, DatabaseInitializeService.class), serviceConnection, 0);
        IInitializeFragment
                .tripper(getSupportFragmentManager())
                .setAddBackStack(false)
                .setLayoutId(R.id.activity_fragment_content)
                .setTag(FRAGMENT_TAG_INITIALIZE)
                .trip();
    }


    @Override
    public void onDestroy() {
        if (serviceBind) {
            unbindService(serviceConnection);
            serviceBind = false;
        }
        super.onDestroy();
    }

}
