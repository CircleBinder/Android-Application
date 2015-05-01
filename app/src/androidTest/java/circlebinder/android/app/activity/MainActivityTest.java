package circlebinder.android.app.activity;

import android.test.ActivityInstrumentationTestCase2;

import com.squareup.spoon.Spoon;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity activity;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        this.activity = getActivity();
    }

    public void test_launch() throws Throwable {
        Spoon.screenshot(activity, "MainActivity_launch");
    }

}
