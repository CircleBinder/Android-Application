package circlebinder.creation.app.phone;

import android.content.Intent;
import android.os.Bundle;

import net.ichigotake.common.app.ActivityTripper;

import circlebinder.common.app.BaseActivity;
import circlebinder.common.app.phone.HomeActivity;
import circlebinder.creation.initialize.LegacyAppStorage;

public final class LauncherActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent launchActivity;
        if (new LegacyAppStorage(getApplicationContext()).isInitialized()) {
            launchActivity = HomeActivity.createIntent(this);
        } else {
            launchActivity = DatabaseInitializeActivity.createIntent(this);
        }
        new ActivityTripper(this, launchActivity)
                .withFinish()
                .trip();
    }
}
