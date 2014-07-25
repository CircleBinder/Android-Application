package circlebinder.creation.app.phone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import net.ichigotake.common.app.ActivityFactory;
import net.ichigotake.common.app.ActivityTripper;
import net.ichigotake.common.app.OnClickToTrip;

import circlebinder.common.Legacy;
import circlebinder.creation.app.BaseActivity;
import circlebinder.R;
import circlebinder.common.app.TripActionProvider;
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
            return;
        }

        setContentView(R.layout.activity_home);
        findViewById(R.id.fragment_checklist_header_label).setOnClickListener(
                new OnClickToTrip(CircleSearchActivity.tripper(this))
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        MenuItem openWebBrowserItem = menu.findItem(R.id.menu_home_open_official_site);
        openWebBrowserItem.setActionProvider(
                new TripActionProvider(this, WebViewActivity.tripper(this, "http://www.creation.gr.jp/"))
        );
        MenuItem contactItem = menu.findItem(R.id.menu_home_wish_me_luck);
        contactItem.setActionProvider(
                new TripActionProvider(this, ContactActivity.tripper(this))
        );
        MenuItem changeLogItem = menu.findItem(R.id.menu_home_change_log);
        changeLogItem.setActionProvider(
                new TripActionProvider(this, ChangeLogActivity.tripper(this))
        );
        MenuItem aboutForApplicationItem = menu.findItem(R.id.menu_home_about);
        aboutForApplicationItem.setActionProvider(
                new TripActionProvider(this, AboutActivity.tripper(this))
        );
        return super.onCreateOptionsMenu(menu);
    }

}
