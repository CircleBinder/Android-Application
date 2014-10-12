package circlebinder.creation.event;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = EventTable.NAME, id = EventTable.FIELD_ID)
public final class EventTable extends Model {

    public static final String NAME = "events";
    public static final String FIELD_ID = "_id";
    public static final String FIELD_EVENT_ID = "event_id";
    public static final String FIELD_EVENT_NUMBER = "event_number";
    public static final String FIELD_EVENT_NAME = "event_name";

    @Column(name = EventBlockTable.FIELD_BLOCK_TYPE_ID)
    public int blockTypeId;

    @Column(name = FIELD_EVENT_ID, index = true)
    public String eventId;

    @Column(name = FIELD_EVENT_NUMBER)
    public String eventNumber;

    @Column(name = FIELD_EVENT_NAME)
    public String eventName;

}


