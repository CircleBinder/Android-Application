package circlebinder.common.event;

import android.test.AndroidTestCase;

import circlebinder.common.test.ParcelUtil;

public final class GenreTest extends AndroidTestCase {

    public void testParcel() {
        Genre expect = new GenreBuilder()
                .setId(653115)
                .setName("ジャンル！")
                .build();

        try {
            Genre got = ParcelUtil.restore(expect);
            assert expect.getId() == got.getId();
            assert expect.getName().equals(got.getName());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
