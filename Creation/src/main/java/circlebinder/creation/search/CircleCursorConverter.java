package circlebinder.creation.search;

import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import net.ichigotake.common.database.CursorSimple;
import net.ichigotake.common.widget.CursorItemConverter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import circlebinder.Legacy;
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
import circlebinder.creation.BaseApplication;
import circlebinder.creation.R;
import circlebinder.creation.event.BlockTable;
import circlebinder.creation.event.CircleTable;

public final class CircleCursorConverter implements CursorItemConverter<Circle>, Legacy {

    private final CircleBuilder circleBuilder = new CircleBuilder();
    private final SpaceBuilder spaceBuilder = new SpaceBuilder();

    @Override
    public Circle create(Cursor cursor) {
        CursorSimple c = new CursorSimple(cursor);
        circleBuilder.clear();
        spaceBuilder.clear();

        Block block = BlockTable.get(c.getLong(CircleTable.Field.BLOCK_ID));
        int spaceNo = c.getInt(CircleTable.Field.SPACE_NO);
        String spaceNoSub = (c.getInt(CircleTable.Field.SPACE_NO_SUB) == 0) ? "a" : "b";

        String spaceSimpleName = String.format(BaseApplication.APP_LOCALE,
                "%s%02d%s", block.getName(), spaceNo, spaceNoSub
        );
        String spaceName = String.format(BaseApplication.APP_LOCALE,
                "%s-%02d%s", block.getName(), spaceNo, spaceNoSub
        );
        spaceBuilder
                .setName(spaceName)
                .setSimpleName(spaceSimpleName)
                .setBlockName(block.getName())
                .setNo(spaceNo)
                .setNoSub(spaceNoSub);

        String name = c.getString(CircleTable.Field.NAME);

        long circleId = c.getLong(CircleTable.Field.ID);
        ChecklistColor checklist = ChecklistColor.getById(
                c.getInt(CircleTable.Field.CHECKLIST_ID)
        );

        //TODO: リンクの種類毎のアイコンを用意する
        List<CircleLink> linkList = new CopyOnWriteArrayList<CircleLink>();
        String homepageUrl = c.getString(CircleTable.Field.HOMEPAGE_URL);
        if (!TextUtils.isEmpty(homepageUrl)) {
            CircleLink link = new CircleLinkBuilder()
                    .setIcon(R.drawable.ic_action_attach)
                    .setUri(Uri.parse(homepageUrl))
                    .setType(CircleLinkType.HOMEPAGE)
                    .build();
            linkList.add(link);
        }
        /*
        String pixivId = c.getString(CircleTable.Field.PIXIV_ID);
        if (!TextUtils.isEmpty(pixivId)) {
            CircleLink link = new CircleLinkBuilder()
                    .setIcon(R.drawable.ic_action_attach)
                    .setUri(Uri.parse("http://www.pixiv.net/member.php?id=" + pixivId))
                    .setType(CircleLinkType.PIXIV)
                    .build();
            linkList.add(link);
        }
        */

        circleBuilder.setLink(new CircleLinks(linkList));

        return circleBuilder
                .setId(circleId)
                .setPenName(c.getString(CircleTable.Field.PEN_NAME))
                .setName(name)
                .setSpace(spaceBuilder.build())
                .setChecklistColor(checklist)
                .setGenre(new GenreBuilder().setName("").build())
                .build();
    }
}
