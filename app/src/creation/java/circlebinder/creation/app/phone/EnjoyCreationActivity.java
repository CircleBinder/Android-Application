package circlebinder.creation.app.phone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import net.ichigotake.common.app.ActivityNavigation;
import net.ichigotake.common.view.MenuPresenter;

import circlebinder.R;
import circlebinder.common.app.ActivityTripActionProvider;
import circlebinder.common.app.BaseActivity;
import circlebinder.common.app.phone.WebViewActivity;

public final class EnjoyCreationActivity extends BaseActivity {

    public static Intent createIntent(Context context) {
        return new Intent(context, EnjoyCreationActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNavigation.setDisplayHomeAsUpEnabled(this);
        setContentView(R.layout.creation_activity_enjoy_creation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuPresenter presenter = new MenuPresenter(menu, getMenuInflater());
        MenuItem openBrowserItem = presenter.inflate(R.menu.open_browser, R.id.menu_open_browser);
        presenter.setActionProvider(openBrowserItem, new ActivityTripActionProvider(
                        this, WebViewActivity.createIntent(this, getString(R.string.app_creation_homepage_top))
                ));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return ActivityNavigation.back(this, item)
                || super.onOptionsItemSelected(item);
    }

}
