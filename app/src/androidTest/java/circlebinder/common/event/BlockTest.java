package circlebinder.common.event;

import android.test.AndroidTestCase;

import circlebinder.common.test.ParcelUtil;

public final class BlockTest extends AndroidTestCase {

    public void testParcelable() {
        Block expect = new BlockBuilder()
                .setId(653115)
                .setName("ブロック名")
                .setArea(new AreaBuilder().setName("area").build())
                .build();

        try {
            Block got = ParcelUtil.restore(expect);
            assertEquals(expect.getId(), got.getId());
            assertEquals(expect.getName(), got.getName());
            assertEquals(expect.getArea().getName(), got.getArea().getName());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
