package circlebinder.creation.app.phone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import net.ichigotake.common.app.ActivityFactory;
import net.ichigotake.common.app.ActivityTripper;

import circlebinder.Legacy;
import circlebinder.creation.BaseActivity;
import circlebinder.R;
import circlebinder.creation.checklist.HomeFragment;
import circlebinder.creation.initialize.AppStorage;

/**
 * 通常起動時のファーストビュー
 */
public final class HomeActivity extends BaseActivity implements Legacy {

    public static ActivityFactory from() {
        return new HomeActivityFactory();
    }

    public static ActivityTripper tripper(Context context) {
        return new ActivityTripper(context, from());
    }

    private static class HomeActivityFactory implements ActivityFactory {

        @Override
        public Intent create(Context context) {
            return new Intent(context, HomeActivity.class);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!new AppStorage(getApplicationContext()).isInitialized()) {
            DatabaseInitializeActivity.tripper(this)
                    .withFinish()
                    .trip();
            return ;
        }

        setContentView(R.layout.circlebinder_activity_basic);
        HomeFragment.tripper(getFragmentManager())
                .setAddBackStack(false)
                .setLayoutId(R.id.activity_fragment_content)
                .trip();
    }

}
