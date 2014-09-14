package circlebinder.creation.event;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = EventCircleTable.NAME, id = EventCircleTable.FIELD_ID)
public class EventCircleTable extends Model {

    public static final String NAME = "event_circles";
    public static final String FIELD_ID = "_id";
    public static final String FIELD_BLOCK_ID = EventBlockTable.FIELD_ID;
    public static final String FIELD_SPACE_NO = "space_no";
    public static final String FIELD_SPACE_NO_SUB = "space_no_sub";
    public static final String FIELD_CIRCLE_NAME =" circle_name";
    public static final String FIELD_PEN_NAME = "pen_name";
    public static final String FIELD_HOMEPAGE = "homepage";
    public static final String FIELD_CHECKLIST_ID = "checklist_id";

    @Column(name = FIELD_BLOCK_ID)
    public int blockId;

    @Column(name = FIELD_SPACE_NO)
    public int spaceNo;

    @Column(name = FIELD_SPACE_NO_SUB)
    public int spaceNoSub;

    @Column(name = FIELD_CIRCLE_NAME)
    public String circleName;

    @Column(name = FIELD_PEN_NAME)
    public String penName;

    @Column(name = FIELD_HOMEPAGE)
    public String homepage;

    @Column(name = FIELD_CHECKLIST_ID)
    public int checklistId;

}
