package circlebinder.reitaisai.system;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ichigotake.common.widget.ArrayAdapter;

final class SystemMenuAdapter extends ArrayAdapter<SystemMenuItem, SystemMenuViewHolder> {

    SystemMenuAdapter(Context context) {
        super(context);
    }

    @Override
    protected View generateView(int position, SystemMenuItem item, LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(SystemMenuViewHolder.layoutResource, parent, false);
    }

    @Override
    protected void bindView(int position, View convertView, SystemMenuItem item, SystemMenuViewHolder tag) {
        tag.getLabel().setCompoundDrawablesWithIntrinsicBounds(item.getIcon(), 0, 0, 0);
        tag.getLabel().setText(item.getLabel());
    }

    @Override
    protected SystemMenuViewHolder generateTag(int position, SystemMenuItem item, View convertView) {
        return new SystemMenuViewHolder(convertView);
    }
}
