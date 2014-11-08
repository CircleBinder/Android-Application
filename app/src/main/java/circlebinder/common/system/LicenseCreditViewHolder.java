package circlebinder.common.system;

import android.view.View;
import android.widget.TextView;

import circlebinder.R;

final class LicenseCreditViewHolder {

    final static int layoutResource = R.layout.common_license_credit_item;

    private final TextView name;
    private final TextView copyright;
    private final TextView licenseName;
    private final TextView licenseBody;

    LicenseCreditViewHolder(View view) {
        this.name = (TextView) view.findViewById(R.id.common_license_credit_item_name);
        this.copyright = (TextView) view.findViewById(R.id.common_license_credit_item_copyright);
        this.licenseName = (TextView) view.findViewById(R.id.common_license_credit_item_license_name);
        this.licenseBody = (TextView) view.findViewById(R.id.common_license_credit_item_license_body);
    }

    TextView getName() {
        return name;
    }

    TextView getCopyright() {
        return copyright;
    }

    TextView getLicenseName() {
        return licenseName;
    }

    TextView getLicenseBody() {
        return licenseBody;
    }

}
