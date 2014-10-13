package circlebinder.common.table;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = EventChecklistHistoryTable.NAME, id = EventChecklistHistoryTable.FIELD_ID)
public final class EventChecklistHistoryTable extends Model {

    public static final String NAME = "event_checklist_history";
    public static final String FIELD_ID = "_id";
    public static final String FIELD_EVENT_ID = EventTable.FIELD_EVENT_ID;
    public static final String FIELD_EVENT_NAME = "event_name";
    public static final String FIELD_EVENT_NUMBER = "event_number";
    public static final String FIELD_SPACE_NAME = "space_name";
    public static final String FIELD_CIRCLE_NAME =" circle_name";
    public static final String FIELD_PEN_NAME = "pen_name";
    public static final String FIELD_HOMEPAGE = "homepage";
    public static final String FIELD_CHECKLIST_ID = "checklist_id";
    public static final String FIELD_CHECKLIST_NAME = "checklist_name";

    @Column(name = FIELD_EVENT_ID)
    public String eventId;

    @Column(name = FIELD_EVENT_NAME)
    public String eventName;

    @Column(name = FIELD_EVENT_NUMBER)
    public String eventNumber;

    @Column(name = FIELD_SPACE_NAME)
    public int spaceName;

    @Column(name = FIELD_CIRCLE_NAME)
    public String circleName;

    @Column(name = FIELD_PEN_NAME)
    public String penName;

    @Column(name = FIELD_HOMEPAGE)
    public String homepage;

    @Column(name = FIELD_CHECKLIST_ID)
    public int checklistId;

    @Column(name = FIELD_CHECKLIST_NAME)
    public int checklistName;

}

