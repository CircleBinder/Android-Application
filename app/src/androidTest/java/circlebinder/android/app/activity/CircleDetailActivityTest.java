package circlebinder.android.app.activity;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;

import com.squareup.spoon.Spoon;

public class CircleDetailActivityTest extends ActivityInstrumentationTestCase2<CircleDetailActivity> {

    private CircleDetailActivity activity;
    private Instrumentation instrumentation;

    public CircleDetailActivityTest() {
        super(CircleDetailActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        this.activity = getActivity();
        this.instrumentation = getInstrumentation();
    }

    public void test_launch() throws Throwable {
        instrumentation.waitForIdleSync();
        Spoon.screenshot(activity, "CircleDetailActivity_launch");
    }

}
