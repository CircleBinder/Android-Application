package circlebinder.common.checklist;

import android.widget.Spinner;

import net.ichigotake.common.widget.OnItemSelectedEventListener;
import net.ichigotake.common.widget.OnItemSelectedListener;

import java.util.Collection;

import circlebinder.common.event.Block;

public final class BlockSelectorContainer {

    private final Spinner selector;
    private final OnItemSelectedListener<Block> onItemSelectedListener;

    public BlockSelectorContainer(Spinner selector, Collection<Block> blocks) {
        this.selector = selector;
        this.onItemSelectedListener = new OnItemSelectedListener<Block>();

        BlockSelectorAdapter adapter = new BlockSelectorAdapter(selector.getContext());
        adapter.clear();
        adapter.addAll(blocks);
        selector.setAdapter(adapter);
        selector.setOnItemSelectedListener(onItemSelectedListener);
    }

    public void addOnItemSelectedListener(OnItemSelectedEventListener<Block> listener) {
        onItemSelectedListener.addOnItemSelectedListener(listener);
    }

    public void setSelection(Block block) {
        final int position;
        if (block == null) {
            position = 0;
        } else if (block.getId() > 0) {
            position = (int)block.getId();
        } else {
            position = 1;
        }
        selector.setSelection(position);
    }

}
