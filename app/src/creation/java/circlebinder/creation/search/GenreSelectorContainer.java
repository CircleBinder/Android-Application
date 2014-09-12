package circlebinder.creation.search;

import android.content.Context;
import android.widget.Spinner;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import circlebinder.common.checklist.BlockSelectorContainer;
import circlebinder.common.event.AllBlock;
import circlebinder.common.event.Block;
import circlebinder.common.event.BlockBuilder;
import circlebinder.creation.event.BlockTable;

public final class GenreSelectorContainer {

    public static BlockSelectorContainer init(Context context, Spinner spinner) {
        List<Block> blocks = new CopyOnWriteArrayList<>();
        blocks.add(new BlockBuilder().setName("全").setId(-1).build());
        blocks.addAll(BlockTable.get());
        BlockSelectorContainer selectorContainer = new BlockSelectorContainer(
                spinner,
                blocks
        );
        selectorContainer.setSelection(new AllBlock(context));
        return selectorContainer;
    }

}
