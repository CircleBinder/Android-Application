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

public final class LicenseCreditListView extends ListView {

    public LicenseCreditListView(Context context) {
        super(context);
    }

    public LicenseCreditListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LicenseCreditListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LicenseCreditListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        LicenseCreditAdapter adapter = new LicenseCreditAdapter(getContext());
        String licenseName = getContext().getString(R.string.common_open_source_license_credit_license_apache_license_v2);
        String licenseBody = "";
        try {
            List<String> lines = new RawResources(getResources()).getText(R.raw.common_license_apache_v2);
            licenseBody = TextUtils.join("\n", lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        adapter.add(new LicenseCredit("AbsListViewHelper", "Felipe Lima", 2011, licenseName, licenseBody));
        adapter.add(new LicenseCredit("ActiveAndroid", "Michael Pardo", 2010, licenseName, licenseBody));
        adapter.add(new LicenseCredit("android-intents", "Dmitriy Tarasov ", 2013, licenseName, licenseBody));
        adapter.add(new LicenseCredit("ltsv4j", "making", 2013, licenseName, licenseBody));
        adapter.add(new LicenseCredit("flow", "Square, Inc.", 2013, licenseName, licenseBody));
        adapter.add(new LicenseCredit("ProgressMenuItem", "Shintaro Katafuchi", 2014, licenseName, licenseBody));
        adapter.add(new LicenseCredit("RxAndroid", "Ben Christensen", 2014, licenseName, licenseBody));
        adapter.add(new LicenseCredit("StickyListHeaders", "Emil Sjolander", 2013, licenseName, licenseBody));

        setAdapter(adapter);
    }

}
