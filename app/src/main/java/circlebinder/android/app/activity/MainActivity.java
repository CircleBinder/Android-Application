package circlebinder.android.app.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import circlebinder.android.app.Logger;
import circlebinder.android.app.R;
import circlebinder.android.app.install.InstallObservableFactory;
import circlebinder.android.app.lifecycle.RxServiceBoundActivity;
import circlebinder.android.app.service.IServiceCompleted;
import circlebinder.android.app.service.BackgroundServiceCommand;
import rx.Observable;

public class MainActivity extends RxServiceBoundActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.as_mock)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(CircleBinderActivity.createIntent(v.getContext()));
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onBackgroundServiceCompleted(IServiceCompleted event) {
        Logger.debug(TAG, "onBackgroundServiceCompleted (IServiceCallbackEvent=" + event + ")");
    }

    @Override
    protected BackgroundServiceCommand createCommand() {
        return new BackgroundServiceCommand() {
            @Override
            public Observable<IServiceCompleted> createObservable() {
                return new InstallObservableFactory(getResources()).createObservable();
            }
        };
    }

}
