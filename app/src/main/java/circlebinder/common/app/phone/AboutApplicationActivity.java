package circlebinder.common.app.phone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import net.ichigotake.common.app.ActivityNavigation;
import net.ichigotake.common.util.Finders;

import circlebinder.common.app.BaseActivity;
import circlebinder.R;
import circlebinder.common.flow.AppFlow;
import circlebinder.common.flow.FlowBundler;
import circlebinder.common.flow.FrameScreenSwitcherView;
import circlebinder.common.flow.HasFlow;
import circlebinder.common.flow.HasTitle;
import circlebinder.common.flow.Screen;
import flow.Backstack;
import flow.Flow;

public final class AboutApplicationActivity extends BaseActivity implements HasFlow {

    public static Intent createIntent(Context context) {
        return new Intent(context, AboutApplicationActivity.class);
    }

    private final FlowBundler flowBundler =
            new FlowBundler(new AboutApplicationScreens.About(), this);
    private AppFlow appFlow;

    private FrameScreenSwitcherView container;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appFlow = flowBundler.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.common_activity_about);
        container = Finders.from(this).findOrNull(R.id.container);
        AppFlow.loadInitialScreen(this);
    }

    @Override
    public Object getSystemService(String name) {
        if (AppFlow.isAppFlowSystemService(name)) return appFlow;
        return super.getSystemService(name);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        flowBundler.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return (item.getItemId() == android.R.id.home && container.onUpPressed())
                || ActivityNavigation.back(this, item)
                || super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!container.onBackPressed()) {
            super.onBackPressed();
        }
    }
    @Override
    public void go(Backstack nextBackstack, Flow.Direction direction, Flow.Callback callback) {
        Screen screen = (Screen)nextBackstack.current().getScreen();
        container.showScreen(screen, direction, callback);
        if (screen instanceof HasTitle) {
            setTitle(((HasTitle) screen).getTitle());
        }
    }

}
