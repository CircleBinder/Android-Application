package circlebinder.creation.event;

import android.database.Cursor;

import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;

import net.ichigotake.common.database.ConditionQueryBuilder;

import circlebinder.common.checklist.ChecklistColor;
import circlebinder.common.event.Circle;
import circlebinder.common.search.CircleOrder;
import circlebinder.common.search.CircleSearchOption;
import circlebinder.common.search.Order;

@Table(name = EventCircleTable.NAME, id = EventCircleTable.FIELD_ID)
public final class EventCircleTable extends Model {

    public static final String NAME = "event_circles";
    public static final String FIELD_ID = "_id";
    public static final String FIELD_BLOCK_ID = "block_id";
    public static final String FIELD_SPACE_NO = "space_no";
    public static final String FIELD_SPACE_NO_SUB = "space_no_sub";
    public static final String FIELD_CIRCLE_NAME = "circle_name";
    public static final String FIELD_PEN_NAME = "pen_name";
    public static final String FIELD_HOMEPAGE = "homepage";
    public static final String FIELD_CHECKLIST_ID = "checklist_id";

    @Column(name = FIELD_BLOCK_ID)
    public long blockId;

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

    public static EventCircleTable find(long circleId) {
        return new Select("*")
                .from(EventCircleTable.class)
                .where(String.format("%s = ?", EventCircleTable.FIELD_ID), circleId)
                .executeSingle();
    }

    public static Cursor get() {
        From query = new Select("*").from(EventCircleTable.class);
        return Cache.openDatabase().rawQuery(query.toSql(), query.getArguments());
    }

    public static Cursor find(CircleSearchOption searchOption) {
        From query = new Select("*").from(EventCircleTable.class);
        ConditionQueryBuilder where = new ConditionQueryBuilder();
        if (searchOption.hasKeyword()) {
            where.and(
                    ConditionQueryBuilder
                            .where(EventCircleTable.FIELD_CIRCLE_NAME + " LIKE ?", "%" + searchOption.getKeyword() + "%")
                            .or(EventCircleTable.FIELD_PEN_NAME + " LIKE ?", "%" + searchOption.getKeyword() + "%")
            );
        }
        if (searchOption.hasBlock() && searchOption.getBlock().getId() > 0) {
            where.and(EventCircleTable.FIELD_BLOCK_ID + " = ?", searchOption.getBlock().getId());
        }
        if (searchOption.hasChecklist()) {
            if (ChecklistColor.isChecklist(searchOption.getChecklist())) {
                where.and(EventCircleTable.FIELD_CHECKLIST_ID + " = ?", searchOption.getChecklist().getId());
            } else {
                where.and(EventCircleTable.FIELD_CHECKLIST_ID + " > ?", 0);
            }
        }
        query.where(where.getQuery(), where.getArguments());
        query.orderBy(buildOrderQuery(searchOption.getOrder()));
        return Cache.openDatabase().rawQuery(query.toSql(), query.getArguments());
    }

    public static void setChecklist(Circle circle, ChecklistColor color) {
        EventCircleTable circleTable = find(circle.getId());
        circleTable.checklistId = color.getId();
        circleTable.save();
    }

    public static String buildOrderQuery(Order order) {
        String query;
        CircleOrder circleOrder;

        if (order instanceof CircleOrder) {
            circleOrder = (CircleOrder)order;
        } else {
            circleOrder = CircleOrder.DEFAULT;
        }

        switch (circleOrder) {
            case CIRCLE_SPACE_ASC:
                query = EventCircleTable.FIELD_SPACE_NO + " ASC, " + EventCircleTable.FIELD_ID + " ASC";
                break;
            case CIRCLE_NAME_ASC:
                query = EventCircleTable.FIELD_CIRCLE_NAME + " ASC, " + EventCircleTable.FIELD_ID + " ASC";
                break;
            case CHECKLIST:
                query = EventCircleTable.FIELD_CHECKLIST_ID + " ASC, " + EventCircleTable.FIELD_ID + " ASC";
                break;
            case DEFAULT:
            default:
                query =  EventCircleTable.FIELD_ID + " ASC";
                break;
        }
        return query;
    }

}
