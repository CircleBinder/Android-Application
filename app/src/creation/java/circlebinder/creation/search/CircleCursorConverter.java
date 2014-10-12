package circlebinder.creation.search;

import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import net.ichigotake.common.database.CursorSimple;
import net.ichigotake.common.widget.CursorItemConverter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import circlebinder.common.Legacy;
import circlebinder.R;
import circlebinder.common.checklist.ChecklistColor;
import circlebinder.common.event.Block;
import circlebinder.common.event.Circle;
import circlebinder.common.event.CircleBuilder;
import circlebinder.common.event.CircleLink;
import circlebinder.common.event.CircleLinkBuilder;
import circlebinder.common.event.CircleLinkType;
import circlebinder.common.event.CircleLinks;
import circlebinder.common.event.GenreBuilder;
import circlebinder.common.event.SpaceBuilder;
import circlebinder.creation.app.CreationBinderApplication;
import circlebinder.creation.event.EventBlockTable;
import circlebinder.creation.event.EventCircleTable;

public final class CircleCursorConverter implements CursorItemConverter<Circle>, Legacy {

    private final CircleBuilder circleBuilder = new CircleBuilder();
    private final SpaceBuilder spaceBuilder = new SpaceBuilder();

    @Override
    public Circle create(Cursor cursor) {
        CursorSimple c = new CursorSimple(cursor);
        circleBuilder.clear();
        spaceBuilder.clear();

        Block block = EventBlockTable.get(c.getLong(EventCircleTable.FIELD_BLOCK_ID));
        int spaceNo = c.getInt(EventCircleTable.FIELD_SPACE_NO);
        String spaceNoSub = (c.getInt(EventCircleTable.FIELD_SPACE_NO_SUB) == 0) ? "a" : "b";

        String spaceSimpleName = String.format(CreationBinderApplication.APP_LOCALE,
                "%s%02d%s", block.getName(), spaceNo, spaceNoSub
        );
        String spaceName = String.format(CreationBinderApplication.APP_LOCALE,
                "%s-%02d%s", block.getName(), spaceNo, spaceNoSub
        );
        spaceBuilder
                .setName(spaceName)
                .setSimpleName(spaceSimpleName)
                .setBlockName(block.getName())
                .setNo(spaceNo)
                .setNoSub(spaceNoSub);

        String name = c.getString(EventCircleTable.FIELD_CIRCLE_NAME);

        long circleId = c.getLong(EventCircleTable.FIELD_ID);
        ChecklistColor checklist = ChecklistColor.getById(
                c.getInt(EventCircleTable.FIELD_CHECKLIST_ID)
        );

        List<CircleLink> linkList = new CopyOnWriteArrayList<>();
        String homepageUrl = c.getString(EventCircleTable.FIELD_HOMEPAGE);
        if (!TextUtils.isEmpty(homepageUrl)) {
            CircleLink link = new CircleLinkBuilder()
                    .setIcon(R.drawable.ic_action_attach)
                    .setUri(Uri.parse(homepageUrl))
                    .setType(CircleLinkType.HOMEPAGE)
                    .build();
            linkList.add(link);
        }

        circleBuilder.setLink(new CircleLinks(linkList));

        return circleBuilder
                .setId(circleId)
                .setPenName(c.getString(EventCircleTable.FIELD_PEN_NAME))
                .setName(name)
                .setSpace(spaceBuilder.build())
                .setChecklistColor(checklist)
                .setGenre(new GenreBuilder().setName("").build())
                .build();
    }
}
