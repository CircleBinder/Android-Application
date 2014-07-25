package circlebinder.creation.app.phone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import net.ichigotake.common.app.ActivityFactory;
import net.ichigotake.common.app.ActivityNavigation;
import net.ichigotake.common.app.ActivityTripper;
import net.ichigotake.common.app.Tripper;
import net.ichigotake.common.os.BundleMerger;

import circlebinder.R;
import circlebinder.common.app.FragmentTripper;
import circlebinder.common.checklist.ChecklistColor;
import circlebinder.common.search.CircleSearchOption;
import circlebinder.common.search.CircleSearchOptionBuilder;
import circlebinder.creation.app.BaseActivity;
import circlebinder.creation.search.CircleSearchFragment;

public final class ChecklistActivity extends BaseActivity {

    private static final String KEY_CHECKLIST_COLOR = "checklist_color";

    public static ActivityFactory factory(ChecklistColor checklistColor) {
        return new ChecklistActivityFactory(checklistColor);
    }

    private static class ChecklistActivityFactory implements ActivityFactory {

        private final ChecklistColor checklistColor;

        private ChecklistActivityFactory(ChecklistColor checklistColor) {
            this.checklistColor = checklistColor;
        }

        @Override
        public Intent create(Context context) {
            Intent intent = new Intent(context, ChecklistActivity.class);
            Bundle map = new Bundle();
            map.putSerializable(KEY_CHECKLIST_COLOR, checklistColor);
            intent.putExtras(map);
            return intent;
        }
    }

    private ChecklistColor checklistColor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_checklist);
        checklistColor = (ChecklistColor) BundleMerger.merge(getIntent(), savedInstanceState)
                .getSerializable(KEY_CHECKLIST_COLOR);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle(checklistColor.getName());
        CircleSearchOption searchOption = new CircleSearchOptionBuilder()
                .setChecklist(checklistColor).build();
        FragmentTripper.firstTrip(
                getFragmentManager(),
                CircleSearchFragment.factory(searchOption)
        )
        .setLayoutId(R.id.activity_checklist_container)
        .trip();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return ActivityNavigation.back(this, menuItem)
                || super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_CHECKLIST_COLOR, checklistColor);
    }
}
