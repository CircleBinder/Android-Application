package circlebinder.creation.event;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import circlebinder.common.Legacy;
import circlebinder.common.event.Block;
import circlebinder.common.event.BlockBuilder;

@Table(name = "Comic1Blocks", id = BlockTable.Field.ID)
public final class BlockTable extends Model implements Legacy {

    public static List<Block> get() {
        List<Block> blocks = new CopyOnWriteArrayList<>();
        List<BlockTable> blockTableList = new Select()
                .from(BlockTable.class)
                .orderBy(Field.ID)
                .execute();
        for (BlockTable item : blockTableList) {
            blocks.add(
                    new BlockBuilder()
                            .setId(item.getId().intValue())
                            .setName(item.name)
                            .build()
            );
        }
        return blocks;
    }

    public static Block get(long id) {
        BlockTable block = new Select()
                .from(BlockTable.class)
                .where(Field.ID + " = ?", id)
                .executeSingle();
        if (block == null) {
            block = new Select()
                    .from(BlockTable.class)
                    .where(Field.ID + " = ?", 1)
                    .executeSingle();
        }
        return new BlockBuilder()
                .setName(block.name)
                .setId(block.getId().intValue())
                .build();
    }

    public static BlockTable get(CharSequence name) {
        return new Select()
                .from(BlockTable.class)
                .where("name = ?", name)
                .executeSingle();
    }

    public static class Field {
        public static final String ID = "blockId";
        public static final String NAME = "name";
    }

    @Column(name = Field.NAME)
    public String name;

}
