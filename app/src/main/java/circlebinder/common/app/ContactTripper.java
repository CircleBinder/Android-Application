package circlebinder.common.app;

import android.content.Context;
import android.content.Intent;

import com.dmitriy.tarasov.android.intents.IntentUtils;

import net.ichigotake.common.app.ActivityTripper;
import net.ichigotake.common.app.Tripper;

import circlebinder.R;

public final class ContactTripper implements Tripper {

    private final Context context;
    private final String appName;

    public ContactTripper(Context context, String appName) {
        this.context = context;
        this.appName = appName;
    }

    @Override
    public void trip() {
        Intent intent = IntentUtils.sendEmail(
                context.getResources().getStringArray(R.array.common_contact_mail_addresses),
                context.getString(R.string.common_contact_mail_subject, appName),
                context.getString(R.string.common_contact_mail_body)
        );
        new ActivityTripper(context, intent).trip();
    }

}