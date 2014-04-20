package circlebinder.creation.event;

import android.database.Cursor;
import android.util.Log;

import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;

import net.ichigotake.common.database.SqlQueryBuilder;

import circlebinder.Legacy;
import circlebinder.common.checklist.ChecklistColor;
import circlebinder.common.event.Circle;
import circlebinder.common.search.CircleOrder;
import circlebinder.common.search.CircleSearchOption;
import circlebinder.common.search.Order;

@Table(name = CircleTable.NAME, id = CircleTable.Field.ID)
public final class CircleTable extends Model implements Legacy {

    public static final String NAME = "CreationCircles";

    public static class Field {
        public final static String ID = "circleId";
        public final static String BLOCK_ID = "blockId";
        public final static String SPACE_NO = "spaceNo";
        public final static String SPACE_NO_SUB = "spaceNoSub";
        public final static String NAME = "name";
        public final static String PEN_NAME = "penName";
        public final static String HOMEPAGE_URL = "homepageUrl";
        public final static String CHECKLIST_ID = "checklistId";
    }

    @Column(name = Field.BLOCK_ID)
    public long blockId;

    @Column(name = Field.SPACE_NO)
    public int spaceNo;

    @Column(name = Field.SPACE_NO_SUB)
    public int spaceNoSub;

    @Column(name = Field.NAME)
    public String name;

    @Column(name = Field.PEN_NAME)
    public String penName;

    @Column(name = Field.HOMEPAGE_URL)
    public String homepageUrl;

    @Column(name = Field.CHECKLIST_ID)
    public int checklistId;

    public static CircleTable find(int circleId) {
        return new Select("*, " + Field.ID + " AS _id")
                .from(CircleTable.class)
                .where(String.format("%s = ?", Field.ID), circleId)
                .executeSingle();
    }

    public static Cursor get() {
        From query = new Select("*, " + Field.ID + " AS _id").from(CircleTable.class);
        return Cache.openDatabase().rawQuery(query.toSql(), query.getArguments());
    }

    public static Cursor get(CircleSearchOption searchOption) {
        From query = new Select("*, " + CircleTable.Field.ID + " AS _id").from(CircleTable.class);
        SqlQueryBuilder.Where where = SqlQueryBuilder.where();
        if (searchOption.hasKeyword()) {
            where.like(CircleTable.Field.NAME, "%" + searchOption.getKeyword() + "%");
        }
        if (searchOption.hasBlock() && searchOption.getBlock().getId() > 0) {
            where.and(Field.BLOCK_ID, searchOption.getBlock().getId());
        }
        if (searchOption.hasChecklist()) {
            if (ChecklistColor.isChecklist(searchOption.getChecklist())) {
                where.and(Field.CHECKLIST_ID, searchOption.getChecklist().getId());
            } else {
                where.and(Field.CHECKLIST_ID, ">", 0);
            }
        }
        query.where(where.getQuery(), where.getArguments());
        query.orderBy(buildOrderQuery(searchOption.getOrder()));
        return Cache.openDatabase().rawQuery(query.toSql(), query.getArguments());
    }

    public static void setChecklist(Circle circle, ChecklistColor color) {
        CircleTable circleTable = find((int) circle.getId());
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
                query = Field.SPACE_NO + " ASC, " + Field.ID + " ASC";
                break;
            case CIRCLE_NAME_ASC:
                query = Field.NAME + " ASC, " + Field.ID + " ASC";
                break;
            case CHECKLIST:
                query = Field.CHECKLIST_ID + " ASC, " + Field.ID + " ASC";
                break;
            case DEFAULT:
            default:
                query =  Field.ID + " ASC";
                break;
        }
        return query;
    }

}
