package circlebinder.common.event;

import android.test.AndroidTestCase;

import circlebinder.common.test.ParcelUtil;

public final class SpaceTest extends AndroidTestCase {

    public void testParcelable() {
        Space expect = new SpaceBuilder()
                .setName("桃")
                .setSimpleName("P")
                .setNo(33)
                .setNoSub("c")
                .setBlockName("ブロック名")
                .build();

        try {
            Space got = ParcelUtil.restore(expect);
            assert expect.getName().equals(got.getName());
            assert expect.getNo() == got.getNo();
            assert expect.getNoSub().equals(got.getNoSub());
            assert expect.getBlockName().equals(got.getBlockName());
            assert expect.getSimpleName().equals(got.getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
