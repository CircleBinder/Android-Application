package circlebinder.creation.app.phone;

import android.content.Intent;
import android.os.Bundle;

import net.ichigotake.common.app.ActivityTripper;

import circlebinder.creation.app.BaseActivity;
import circlebinder.creation.initialize.AppStorage;

public final class LauncherActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent launchActivity;
        if (new AppStorage(getApplicationContext()).isInitialized()) {
            launchActivity = HomeActivity.createIntent(this);
        } else {
            launchActivity = DatabaseInitializeActivity.createIntent(this);
        }
        new ActivityTripper(this, launchActivity)
                .withFinish()
                .trip();
    }
}
