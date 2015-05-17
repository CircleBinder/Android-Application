package circlebinder.android.app.activity;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;

import com.squareup.spoon.Spoon;

public class CircleBinderActivityTest extends ActivityInstrumentationTestCase2<CircleBinderActivity> {

    private CircleBinderActivity activity;
    private Instrumentation instrumentation;

    public CircleBinderActivityTest() {
        super(CircleBinderActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        this.activity = getActivity();
        this.instrumentation = getInstrumentation();
    }

    public void test_launch() throws Throwable {
        instrumentation.waitForIdleSync();
        Spoon.screenshot(activity, "CircleBinderActivity_launch");
    }

}
