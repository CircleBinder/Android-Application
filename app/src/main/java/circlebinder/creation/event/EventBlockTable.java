package circlebinder.creation.event;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import circlebinder.common.event.Block;
import circlebinder.common.event.BlockBuilder;

@Table(name = EventBlockTable.NAME, id = EventBlockTable.FIELD_ID)
public final class EventBlockTable extends Model {

    public static final String NAME = "event_blocks";
    public static final String FIELD_ID = "_id";
    public static final String FIELD_BLOCK_TYPE_ID = "block_type_id";
    public static final String FIELD_BLOCK_NAME = "block_name";

    @Column(name = FIELD_BLOCK_TYPE_ID, index = true)
    public int blockTypeId;

    @Column(name = FIELD_BLOCK_NAME)
    public String blockName;

    public static List<Block> getAll() {
        List<Block> blocks = new CopyOnWriteArrayList<>();
        List<EventBlockTable> blockTableList = new Select()
                .from(EventBlockTable.class)
                .orderBy(EventBlockTable.FIELD_BLOCK_TYPE_ID)
                .execute();
        for (EventBlockTable item : blockTableList) {
            blocks.add(
                    new BlockBuilder()
                            .setId(item.getId())
                            .setName(item.blockName)
                            .setType(EventBlockType.get(item.blockTypeId))
                            .build()
            );
        }
        return blocks;
    }

    public static EventBlockTable get(CharSequence name) {
        return new Select()
                .from(EventBlockTable.class)
                .where(FIELD_BLOCK_NAME + " = ?", name)
                .executeSingle();
    }

    public static Block get(long id) {
        EventBlockTable block = new Select()
                .from(EventBlockTable.class)
                .where(EventBlockTable.FIELD_ID + " = ?", id)
                .executeSingle();
        if (block == null) {
            block = new Select()
                    .from(EventBlockTable.class)
                    .where(EventBlockTable.FIELD_ID + " = ?", 1)
                    .executeSingle();
        }
        return new BlockBuilder()
                .setName(block.blockName)
                .setId(block.getId())
                .setType(EventBlockType.get(block.blockTypeId))
                .build();
    }

}
