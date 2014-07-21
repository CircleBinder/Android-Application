package circlebinder.common.event;

import android.net.Uri;
import android.test.AndroidTestCase;

import circlebinder.common.checklist.ChecklistColor;
import circlebinder.common.test.ParcelUtil;

public final class CircleTest extends AndroidTestCase {

    public void testParcel() {
        Circle expect = new CircleBuilder()
                .setId(653115)
                .setName("銀河ドリーム")
                .setPenName("銀河宇宙人")
                .setChecklistColor(ChecklistColor.GREEN)
                .setGenre(new GenreBuilder().setName("銀河ジャンル").build())
                .setSpace(new SpaceBuilder().setName("銀河スペース").build())
                .addLink(new CircleLinkBuilder().setUri(Uri.parse("http://my.homepage.com")).build())
                .build();

        try {
            Circle got = ParcelUtil.restore(expect);
            assert expect.getId() == got.getId();
            assert expect.getName().equals(got.getName());
            assert expect.getPenName().equals(got.getPenName());
            assert expect.getChecklistColor() == got.getChecklistColor();
            assert expect.getGenre().getName().equals(got.getGenre().getName());
            assert expect.getSpace().getName().equals(got.getSpace().getName());
            assert expect.getLinks().equals(got.getLinks());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
