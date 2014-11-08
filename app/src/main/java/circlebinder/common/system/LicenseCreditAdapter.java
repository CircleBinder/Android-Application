package circlebinder.common.system;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ichigotake.common.widget.ArrayAdapter;

public final class LicenseCreditAdapter
        extends ArrayAdapter<LicenseCredit, Void> {

    public LicenseCreditAdapter(Context context) {
        super(context);
    }

    @Override
    protected View generateView(int position, LicenseCredit item, LayoutInflater inflater, ViewGroup parent) {
        return new LicenseCreditView(getContext());
    }

    @Override
    protected void bindView(int position, View convertView, LicenseCredit item, Void tag) {
        ((LicenseCreditView)convertView).setCredit(item);
    }

    @Override
    protected Void generateTag(int position, LicenseCredit item, View convertView) {
        return null;
    }

}
