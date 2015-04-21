package circlebinder.android.app;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class HelloWorldTest {

    @Test
    public void test_helloWorld() throws Exception {
        Activity activity = Robolectric.setupActivity(MainActivity.class);

        TextView label = (TextView) activity.findViewById(R.id.activity_main_hello_world);
        assertTrue(TextUtils.equals("Hello world!", label.getText()));
    }
}
