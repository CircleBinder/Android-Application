package circlebinder.common.checklist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Spinner;

import net.ichigotake.common.widget.OnItemSelectedEventListener;
import net.ichigotake.common.widget.OnItemSelectedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import circlebinder.R;
import circlebinder.common.event.Block;
import circlebinder.common.event.BlockBuilder;

public final class EventBlockSelectorView extends FrameLayout {

    private BlockSelectorAdapter adapter;
    private Spinner selector;
    private OnItemSelectedListener<Block> onItemSelectedListener;

    @SuppressWarnings("unused") // Public API
    public EventBlockSelectorView(Context context) {
        super(context);
        initialize();
    }

    @SuppressWarnings("unused") // Public API
    public EventBlockSelectorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    @SuppressWarnings("unused") // Public API
    public EventBlockSelectorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    private void initialize() {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.common_event_block_selector, this, true);
        this.onItemSelectedListener = new OnItemSelectedListener<>();
        this.selector = (Spinner) view.findViewById(R.id.common_view_event_block_selector);
        this.adapter = new BlockSelectorAdapter(getContext());
        this.selector.setAdapter(adapter);
        this.selector.setOnItemSelectedListener(onItemSelectedListener);
    }

    //TODO: ブロック名にsuffixとして「 ブロック」をつけている。実装を見直すべき
    public void setBlockList(List<Block> blocks) {
        List<Block> tmpBlocks = new CopyOnWriteArrayList<>();
        tmpBlocks.add(new BlockBuilder().setName("全").setId(-1).build());
        tmpBlocks.addAll(blocks);
        List<Block> setBlocks = new CopyOnWriteArrayList<>();
        for (Block block : tmpBlocks) {
            setBlocks.add(new BlockBuilder()
                    .setArea(block.getArea())
                    .setName(block.getName() + " ブロック")
                    .setId(block.getId())
                    .build());
        }
        this.adapter.clear();
        this.adapter.addAll(setBlocks);
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
            position = 0;
        }
        selector.setSelection(position);
    }

}
