package circlebinder.common.changelog;

import android.test.AndroidTestCase;

import circlebinder.common.test.ParcelUtil;

public final class PublishDateTest extends AndroidTestCase {

    public void testParselable() {
        PublishDate expect = new PublishDate.Builder()
                .setFormattedDate("2013-04-05")
                .setTimestamp(100)
                .build();

        try {
            PublishDate got = ParcelUtil.restore(expect);
            assertEquals(expect.getFormattedDate(), got.getFormattedDate());
            assertEquals(expect.getTimestamp(), got.getTimestamp());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
