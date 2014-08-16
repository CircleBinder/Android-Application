package circlebinder.creation.app.phone;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import net.ichigotake.common.app.ActivityTripper;
import net.ichigotake.common.app.FragmentPagerAdapter;

import circlebinder.common.Legacy;
import circlebinder.creation.app.BaseActivity;
import circlebinder.R;
import circlebinder.common.app.TripActionProvider;
import circlebinder.creation.enjoy.EnjoyCreationFragmentPagerItem;
import circlebinder.creation.initialize.AppStorage;

/**
 * 通常起動時のファーストビュー
 */
public final class HomeActivity extends BaseActivity implements Legacy {

    public static Intent createIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!new AppStorage(getApplicationContext()).isInitialized()) {
            new ActivityTripper(this, DatabaseInitializeActivity.createIntent(this))
                    .withFinish()
                    .trip();
            return;
        }

        getActionBar().setTitle(R.string.app_event_name);
        setContentView(R.layout.activity_home);
        ViewPager enjoyCreationPager = (ViewPager) findViewById(R.id.activity_home_enjoy_creation);
        FragmentPagerAdapter enjoyCreationPagerAdapter =
                new FragmentPagerAdapter(getFragmentManager(), new EnjoyCreationFragmentPagerItem());
        enjoyCreationPager.setAdapter(enjoyCreationPagerAdapter);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ActionBar actionBar = getActionBar();
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            actionBar.setDisplayShowTitleEnabled(true);
            findViewById(R.id.activity_home_header_event_name).setVisibility(View.GONE);
        } else {
            actionBar.setDisplayShowTitleEnabled(false);
            findViewById(R.id.activity_home_header_event_name).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        MenuItem openWebBrowserItem = menu.findItem(R.id.menu_home_open_official_site);
        openWebBrowserItem.setActionProvider(
                new TripActionProvider(
                        this,
                        new ActivityTripper(
                                this,
                                WebViewActivity.createIntent(this, "http://www.creation.gr.jp/")
                        )
                )
        );
        MenuItem contactItem = menu.findItem(R.id.menu_home_wish_me_luck);
        contactItem.setActionProvider(
                new TripActionProvider(
                        this,
                        new ActivityTripper(this, ContactActivity.createIntent(this))
                )
        );
        MenuItem changeLogItem = menu.findItem(R.id.menu_home_change_log);
        changeLogItem.setActionProvider(
                new TripActionProvider(
                        this,
                        new ActivityTripper(this, ChangeLogActivity.createIntent(this))
                )
        );
        MenuItem aboutForApplicationItem = menu.findItem(R.id.menu_home_about);
        aboutForApplicationItem.setActionProvider(
                new TripActionProvider(
                        this,
                        new ActivityTripper(this, AboutActivity.createIntent(this))
                )
        );
        return super.onCreateOptionsMenu(menu);
    }

}
