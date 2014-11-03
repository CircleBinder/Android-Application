package circlebinder.common.system;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ListView;

import net.ichigotake.common.content.RawResources;

import java.io.IOException;
import java.util.List;

import circlebinder.R;

public final class OpenSourceLicenseCreditListView extends ListView {

    public OpenSourceLicenseCreditListView(Context context) {
        super(context);
    }

    public OpenSourceLicenseCreditListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OpenSourceLicenseCreditListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public OpenSourceLicenseCreditListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        OpenSourceLicenseCreditAdapter adapter = new OpenSourceLicenseCreditAdapter(getContext());
        String licenseBody = "";
        try {
            List<String> lines = new RawResources(getResources()).getText(R.raw.common_license_apache_v2);
            licenseBody = TextUtils.join("\n", lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        adapter.add(new OpenSourceLicenseCredit("AbsListViewHelper", "Felipe Lima", 2011, licenseBody));
        adapter.add(new OpenSourceLicenseCredit("ActiveAndroid", "Michael Pardo", 2010, licenseBody));
        adapter.add(new OpenSourceLicenseCredit("android-intents", "Dmitriy Tarasov ", 2013, licenseBody));
        adapter.add(new OpenSourceLicenseCredit("ltsv4j", "making", 2013, licenseBody));
        adapter.add(new OpenSourceLicenseCredit("flow", "Square, Inc.", 2013, licenseBody));
        adapter.add(new OpenSourceLicenseCredit("Picasso", "Square, Inc", 2013, licenseBody));
        adapter.add(new OpenSourceLicenseCredit("ProgressMenuItem", "Shintaro Katafuchi", 2014, licenseBody));
        adapter.add(new OpenSourceLicenseCredit("RxAndroid", "Ben Christensen", 2014, licenseBody));
        adapter.add(new OpenSourceLicenseCredit("StickyListHeaders", "Emil Sjolander", 2013, licenseBody));

        setAdapter(adapter);
    }

}
