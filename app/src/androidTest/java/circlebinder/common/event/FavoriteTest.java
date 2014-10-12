package circlebinder.common.event;

import android.test.AndroidTestCase;

import circlebinder.common.checklist.ChecklistColor;
import circlebinder.common.test.ParcelUtil;

public final class FavoriteTest extends AndroidTestCase {
    
    public void testParcelable() {
        Favorite expect = new FavoriteBuilder()
                .setChecklistColor(ChecklistColor.LIGHT_BLUE)
                .setCircle(new CircleBuilder().setName("サークル名").build())
                .build();

        try {
            Favorite got = ParcelUtil.restore(expect);
            assert expect.getChecklist() == got.getChecklist();
            assert expect.getCircle().getName().equals(got.getCircle().getName());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
