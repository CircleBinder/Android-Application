package circlebinder.android.app;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void test_helloWorld() throws Throwable {
        MainActivity activity = getActivity();
        TextView helloWorld = (TextView) activity.findViewById(R.id.activity_main_hello_world);
        assertEquals(activity.getString(R.string.hello_world), helloWorld.getText());
    }

}
