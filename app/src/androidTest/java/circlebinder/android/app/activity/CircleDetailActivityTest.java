package circlebinder.android.app.activity;

import android.test.ActivityInstrumentationTestCase2;

import com.squareup.spoon.Spoon;

public class CircleDetailActivityTest extends ActivityInstrumentationTestCase2<CircleDetailActivity> {

    private CircleDetailActivity activity;

    public CircleDetailActivityTest() {
        super(CircleDetailActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        this.activity = getActivity();
    }

    public void test_launch() throws Throwable {
        Spoon.screenshot(activity, "CircleDetailActivity_launch");
    }

}
