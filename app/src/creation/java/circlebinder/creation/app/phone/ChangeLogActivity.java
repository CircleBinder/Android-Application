package circlebinder.creation.app.phone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import net.ichigotake.common.app.ActivityNavigation;

import circlebinder.creation.app.BaseActivity;
import circlebinder.R;

public final class ChangeLogActivity extends BaseActivity {

    public static Intent createIntent(Context context) {
        return new Intent(context, ChangeLogActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_log);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return ActivityNavigation.back(this, menuItem)
                || super.onOptionsItemSelected(menuItem);
    }

}
