package circlebinder.creation.search;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import circlebinder.R;
import circlebinder.common.checklist.BlockSelectorContainer;
import circlebinder.common.event.AllBlock;
import circlebinder.common.event.Block;
import circlebinder.common.event.BlockBuilder;
import circlebinder.creation.app.BaseFragment;
import circlebinder.creation.event.EventBlockTable;

public final class BlockSelectorFragment extends BaseFragment {

    private BlockSelectorContainer container;
    private OnBlockSelectListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof OnBlockSelectListener)) {
            throw new IllegalStateException("Activity must implements OnBlockSelectListener");
        }
        listener = (OnBlockSelectListener)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.circle_search_option, parent, false);
        List<Block> preBlocks = new CopyOnWriteArrayList<>();
        preBlocks.add(new BlockBuilder().setName("全").setId(-1).build());
        preBlocks.addAll(EventBlockTable.getAll());
        List<Block> blocks = new CopyOnWriteArrayList<>();
        for (Block block : preBlocks) {
            blocks.add(new BlockBuilder()
                    .setArea(block.getArea())
                    .setName(block.getName() + " ブロック")
                    .setId(block.getId())
                    .build());
        }
        container = new BlockSelectorContainer(
                (Spinner) view.findViewById(R.id.circle_search_option_block_selector),
                blocks
        );
        container.setSelection(new AllBlock(getActivity()));
        container.addOnItemSelectedListener(listener::onBlockSelect);
        return view;
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    public void setSelection(Block block) {
        container.setSelection(block);
    }

}
