package circlebinder.android.app.activity;

import android.os.Bundle;

import circlebinder.android.app.R;
import circlebinder.android.app.lifecycle.RxActivity;

public class CircleDetailActivity extends RxActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_detail);
    }

}
