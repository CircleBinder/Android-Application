package circlebinder.common.app;

import android.content.Context;
import android.content.Intent;

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
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(
                Intent.EXTRA_EMAIL,
                context.getResources().getStringArray(R.array.circlebinder_navigation_contact_mail_addresses)
        );

        intent.putExtra(
                Intent.EXTRA_SUBJECT,
                context.getString(R.string.circlebinder_contact_mail_subject, appName)
        );
        intent.putExtra(
                Intent.EXTRA_TEXT,
                context.getString(R.string.circlebinder_contact_mail_body)
        );
        intent.setType("message/rfc822");
        context.startActivity(intent);
    }
}