package circlebinder.common.event;

import android.test.AndroidTestCase;

import circlebinder.common.test.ParcelUtil;

public final class AreaTest extends AndroidTestCase {

    public void testParcelable() {
        Area expect = new AreaBuilder()
                .setName("Name!")
                .setSimpleName("SimpleName!")
                .build();

        try {
            Area got = ParcelUtil.restore(expect);
            assertEquals(expect.getName(), got.getName());
            assertEquals(expect.getSimpleName(), got.getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
