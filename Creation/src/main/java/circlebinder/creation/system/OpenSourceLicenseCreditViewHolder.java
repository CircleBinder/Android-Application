package circlebinder.creation.system;

import android.view.View;
import android.widget.TextView;

import circlebinder.creation.R;

public final class OpenSourceLicenseCreditViewHolder {

    private final TextView name;
    private final TextView copyright;
    private final TextView licenseBody;

    public OpenSourceLicenseCreditViewHolder(View view) {
        this.name = (TextView) view.findViewById(R.id.circlebinder_license_credit_item_name);
        this.copyright = (TextView) view.findViewById(R.id.circlebinder_license_credit_item_copyright);
        this.licenseBody = (TextView) view.findViewById(R.id.circlebinder_license_credit_item_license_body);
    }

    public TextView getName() {
        return name;
    }

    public TextView getCopyright() {
        return copyright;
    }

    public TextView getLicenseBody() {
        return licenseBody;
    }

}
