package circlebinder.creation.event;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = EventBlockTable.NAME, id = EventBlockTable.FIELD_ID)
public final class EventBlockTable extends Model {

    public static final String NAME = "event_blocks";
    public static final String FIELD_ID = "_id";
    public static final String FIELD_BLOCK_TYPE_ID = "block_type_id";
    public static final String FIELD_BLOCK_NAME = "block_name";

    @Column(name = FIELD_BLOCK_TYPE_ID, index = true)
    public String blockTypeId;

    @Column(name = FIELD_BLOCK_NAME)
    public String blockName;

}
