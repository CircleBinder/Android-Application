package circlebinder.creation.app.phone;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.view.View;

import net.ichigotake.common.app.ActivityTripper;
import net.ichigotake.common.app.OnClickToTrip;

import circlebinder.common.Legacy;
import circlebinder.common.app.BaseActivity;
import circlebinder.R;
import circlebinder.creation.initialize.DatabaseInitializeService;
import circlebinder.creation.initialize.IInitializeBindService;
import circlebinder.creation.initialize.IInitializeServiceCallback;

public final class DatabaseInitializeActivity extends BaseActivity
        implements Legacy {

    public static Intent createIntent(Context context) {
        return new Intent(context, DatabaseInitializeActivity.class);
    }

    private boolean serviceBind;
    private Handler handler;
    private IInitializeBindService mService;
    private IInitializeServiceCallback callback = new IInitializeServiceCallback.Stub() {
        @Override
        public void initializeCompleted() throws RemoteException {
            Message message = Message.obtain();
            handler.sendMessage(message);
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
        setContentView(R.layout.creation_activity_database_initialize);
        serviceBind = true;
        bindService(new Intent(this, DatabaseInitializeService.class), serviceConnection, 0);
        View finishedView = findViewById(R.id.creation_activity_initialize_finished);
        finishedView.setOnClickListener(new OnClickToTrip(
                new ActivityTripper(this, HomeActivity.createIntent(this)).withFinish()
        ));
        handler = new InitializeHandler(
                findViewById(R.id.creation_activity_initialize_progress),
                finishedView
        );
        startService(new Intent(this, DatabaseInitializeService.class));
    }

    @Override
    public void onDestroy() {
        if (serviceBind) {
            unbindService(serviceConnection);
            serviceBind = false;
        }
        super.onDestroy();
    }

    private static class InitializeHandler extends Handler {

        private final View progressView;
        private final View finishedView;

        private InitializeHandler(View progressView, View finishedView) {
            this.progressView = progressView;
            this.finishedView = finishedView;
        }

        @Override
        public void handleMessage(Message message) {
            progressView.setVisibility(View.GONE);
            finishedView.setVisibility(View.VISIBLE);
        }
    }

}
