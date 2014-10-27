package circlebinder.creation.app.phone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import net.ichigotake.common.view.MenuPresenter;

import circlebinder.common.app.ActivityTripActionProvider;

import circlebinder.common.Legacy;
import circlebinder.common.app.BaseActivity;
import circlebinder.R;
import circlebinder.common.app.BroadcastEvent;
import circlebinder.common.app.phone.AboutActivity;
import circlebinder.common.app.phone.ContactActivity;
import circlebinder.common.card.HomeCardListView;
import circlebinder.creation.home.CreationHomepageCard;
import circlebinder.creation.home.CreationLocationCard;
import circlebinder.creation.home.CreationOfficialTwitterCard;
import circlebinder.creation.home.CreationTwitterHashTagCard;

/**
 * 通常起動時のファーストビュー
 */
public final class HomeActivity extends BaseActivity implements Legacy {

    public static Intent createIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    private BroadcastReceiver broadcastReceiver;
    private HomeCardListView homeCardListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creation_activity_home);

        homeCardListView = (HomeCardListView) findViewById(R.id.creation_activity_home_fragment_content);
        homeCardListView.addItem(new CreationHomepageCard(this));
        homeCardListView.addItem(new CreationLocationCard(this));
        homeCardListView.addItem(new CreationOfficialTwitterCard(this));
        homeCardListView.addItem(new CreationTwitterHashTagCard(this));
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                homeCardListView.reload();
            }
        };
        registerReceiver(broadcastReceiver, BroadcastEvent.createIntentFilter());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuPresenter presenter = new MenuPresenter(menu, getMenuInflater());
        MenuItem feedbackItem = presenter.inflate(R.menu.wish_me_luck, R.id.menu_wish_me_luck);
        presenter.setActionProvider(
                feedbackItem,
                new ActivityTripActionProvider(this, ContactActivity.createIntent(this)));
        MenuItem changeLogItem = presenter.inflate(R.menu.change_log, R.id.menu_change_log);
        presenter.setActionProvider(
                changeLogItem,
                new ActivityTripActionProvider(this, ChangeLogActivity.createIntent(this)));
        MenuItem aboutApplicationItem = presenter.inflate(R.menu.about_application, R.id.menu_about_application);
        presenter.setActionProvider(
                aboutApplicationItem,
                new ActivityTripActionProvider(this, AboutActivity.createIntent(this)));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onDestroy() {
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
        super.onDestroy();
    }

}
