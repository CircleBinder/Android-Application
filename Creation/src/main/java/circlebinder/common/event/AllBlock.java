package circlebinder.common.event;

import android.content.Context;

import circlebinder.creation.R;

public final class AllBlock extends Block {

    public AllBlock(Context context) {
        super(
                new BlockBuilder()
                        .setId(-1)
                        .setName(context.getString(R.string.circlebinder_common_all))
        );
    }
}
