package circlebinder.common.checklist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ichigotake.common.widget.ArrayAdapter;

import circlebinder.creation.R;


final class ChecklistSelectorAdapter extends ArrayAdapter<ChecklistColor, SelectorViewHolder> {

    public ChecklistSelectorAdapter(Context context) {
        super(context);
    }

    @Override
    public SelectorViewHolder generateTag(int position, ChecklistColor item, View convertView) {
        return new SelectorViewHolder(convertView);
    }

    @Override
    public View generateView(int position, ChecklistColor item, LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.circlebinder_spinner_dropdown_item, parent, false);
    }

    @Override
    public void bindView(int position, ChecklistColor item, SelectorViewHolder tag) {
        tag.getName().setText(item.getName());
        tag.getIcon().setImageResource(item.getDrawableResource());
    }

}
