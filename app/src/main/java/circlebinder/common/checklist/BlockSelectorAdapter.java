package circlebinder.common.checklist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ichigotake.common.widget.ArrayAdapter;

import circlebinder.common.event.Block;
import circlebinder.R;

public final class BlockSelectorAdapter extends ArrayAdapter<Block, BlockSelectorViewHolder> {

    public BlockSelectorAdapter(Context context) {
        super(context);
    }

    @Override
    public BlockSelectorViewHolder generateTag(int position, Block item, View convertView) {
        return new BlockSelectorViewHolder(convertView);
    }

    @Override
    public View generateView(int position, Block item, LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.common_spinner_dropdown_item, parent, false);
    }

    @Override
    public void bindView(int position, View convertView, Block item, BlockSelectorViewHolder tag) {
        tag.getName().setText(item.getName());
        tag.getIcon().setVisibility(View.GONE);
    }
}
