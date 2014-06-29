package circlebinder.creation.app.phone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import net.ichigotake.common.app.ActivityFactory;
import net.ichigotake.common.app.ActivityNavigation;
import net.ichigotake.common.app.ActivityTripper;

import circlebinder.common.app.FragmentTripper;
import circlebinder.creation.BaseActivity;
import circlebinder.creation.R;
import circlebinder.creation.search.CircleSearchFragment;

public final class CircleSearchActivity extends BaseActivity {

    public static ActivityTripper tripper(Context context) {
        return new ActivityTripper(context, new CircleSearchActivityFactory());
    }

    private static class CircleSearchActivityFactory implements ActivityFactory {

        @Override
        public Intent create(Context context) {
            return new Intent(context, CircleSearchActivity.class);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circlebinder_activity_basic);
        new FragmentTripper(getFragmentManager(), CircleSearchFragment.factory())
                .setAddBackStack(false)
                .setLayoutId(R.id.activity_fragment_content)
                .trip();

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return ActivityNavigation.back(this, menuItem)
                || super.onOptionsItemSelected(menuItem);
    }

}
