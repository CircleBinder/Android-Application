package circlebinder.android.app.activity;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;

import com.squareup.spoon.Spoon;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity activity;
    private Instrumentation instrumentation;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        this.activity = getActivity();
        this.instrumentation = getInstrumentation();
    }

    public void test_launch() throws Throwable {
        instrumentation.waitForIdleSync();
        Spoon.screenshot(activity, "MainActivity_launch");
    }

}
