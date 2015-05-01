package circlebinder.android.app.activity;

import android.test.ActivityInstrumentationTestCase2;

import com.squareup.spoon.Spoon;

public class CircleBinderActivityTest extends ActivityInstrumentationTestCase2<CircleBinderActivity> {

    private CircleBinderActivity activity;

    public CircleBinderActivityTest() {
        super(CircleBinderActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        this.activity = getActivity();
    }

    public void test_launch() throws Throwable {
        Spoon.screenshot(activity, "CircleBinderActivity_launch");
    }

}
