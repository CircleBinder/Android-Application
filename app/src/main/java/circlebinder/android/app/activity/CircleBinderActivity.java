package circlebinder.android.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import circlebinder.android.app.R;
import circlebinder.android.app.ActivityIntentFactory;
import circlebinder.android.app.lifecycle.RxActivity;

public class CircleBinderActivity extends RxActivity {

    public static ActivityIntentFactory from() {
        return new ActivityIntentFactory() {
            @Override
            public Intent createIntent(Context context) {
                return new Intent(context, CircleBinderActivity.class);
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circlebinder);
        findViewById(R.id.activity_circlebinder_circle_item)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(CircleDetailActivity.from().createIntent(v.getContext()));
                    }
                });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }

}