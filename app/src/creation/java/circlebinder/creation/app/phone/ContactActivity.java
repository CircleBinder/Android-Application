package circlebinder.creation.app.phone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import net.ichigotake.common.app.ActivityFactory;
import net.ichigotake.common.app.ActivityNavigation;

import circlebinder.creation.app.BaseActivity;
import circlebinder.R;

public final class ContactActivity extends BaseActivity {

    public static ActivityFactory factory() {
        return new ActivityFactory() {
            @Override
            public Intent create(Context context) {
                return new Intent(context, ContactActivity.class);
            }
        };
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return ActivityNavigation.back(this, menuItem)
                || super.onOptionsItemSelected(menuItem);
    }

}
