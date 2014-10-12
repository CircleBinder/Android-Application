package circlebinder.common.system;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ichigotake.common.widget.ArrayAdapter;

import circlebinder.R;

public final class OpenSourceLicenseCreditAdapter
        extends ArrayAdapter<OpenSourceLicenseCredit, OpenSourceLicenseCreditViewHolder> {

    public OpenSourceLicenseCreditAdapter(Context context) {
        super(context);
    }

    @Override
    protected View generateView(int position, OpenSourceLicenseCredit item, LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.common_license_credit_item, parent, false);
    }

    @Override
    protected void bindView(int position, OpenSourceLicenseCredit item, OpenSourceLicenseCreditViewHolder tag) {
        tag.getName().setText(item.getName());
        tag.getCopyright().setText("Copyright " + item.getSince() + " " + item.getAuthor());
        tag.getLicenseBody().setText(item.getLicenseBody());
    }

    @Override
    protected OpenSourceLicenseCreditViewHolder generateTag(int position, OpenSourceLicenseCredit item, View convertView) {
        return new OpenSourceLicenseCreditViewHolder(convertView);
    }

}
