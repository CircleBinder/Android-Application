package circlebinder.common.card;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ichigotake.common.widget.ArrayAdapter;

import circlebinder.R;

public final class HomeCardAdapter extends ArrayAdapter<HomeCard, HomeCardItemViewHolder> {

    private final Resources resources;

    public HomeCardAdapter(Context context) {
        super(context);
        this.resources = context.getResources();
    }

    @Override
    public View generateView(int position, HomeCard item, LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.common_home_card_item, parent, false);
        ViewCompat.setElevation(view, 10);
        return view;
    }

    @Override
    public void bindView(int position, View convertView, HomeCard item, HomeCardItemViewHolder holder) {
        holder.getLabel().setCompoundDrawablesWithIntrinsicBounds(
                resources.getDrawable(item.getBackgroundResource()), null, null, null
        );
        holder.getLabel().setText(item.getLabel());
        holder.getCaption().setText(item.getCaption());
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public HomeCardItemViewHolder generateTag(int position, HomeCard item, View convertView) {
        return new HomeCardItemViewHolder(convertView);
    }

}
