package circlebinder.common.system;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import net.ichigotake.common.view.OnClickToToggleView;

import circlebinder.R;

public class LicenseCreditView extends FrameLayout {

    private final LicenseCreditViewHolder holder;

    public LicenseCreditView(Context context) {
        super(context);
        View container = LayoutInflater.from(context)
                .inflate(LicenseCreditViewHolder.layoutResource, this, true);
        holder = new LicenseCreditViewHolder(container);
        container.setOnClickListener(new OnClickToToggleView(holder.getLicenseBody()));
        holder.getLicenseBody().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.getLicenseBody().setVisibility(View.GONE);
            }
        });
    }

    public void setCredit(LicenseCredit credit) {
        holder.getName().setText(credit.getName());
        CharSequence copyright = getContext()
                .getString(R.string.common_open_source_license_credit_copyright,
                        credit.getSince(), credit.getAuthor());
        holder.getCopyright().setText(copyright);
        holder.getLicenseName().setText(credit.getLicenseName());
        holder.getLicenseBody().setText(credit.getLicenseBody());
    }

}
