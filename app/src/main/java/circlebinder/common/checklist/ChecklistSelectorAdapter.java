package circlebinder.common.checklist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ichigotake.common.widget.ArrayAdapter;

import circlebinder.R;


final class ChecklistSelectorAdapter extends ArrayAdapter<ChecklistColor, ChecklistSelectorViewHolder> {

    public ChecklistSelectorAdapter(Context context) {
        super(context);
    }

    @Override
    public ChecklistSelectorViewHolder generateTag(int position, ChecklistColor item, View convertView) {
        return new ChecklistSelectorViewHolder(convertView);
    }

    @Override
    public View generateView(int position, ChecklistColor item, LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.common_checklist_selector_dropdown_item, parent, false);
    }

    @Override
    public void bindView(int position, View convertView, ChecklistColor item, ChecklistSelectorViewHolder tag) {
        tag.getName().setText(item.getName());
        tag.getIcon().setChecklist(item);
    }

}
