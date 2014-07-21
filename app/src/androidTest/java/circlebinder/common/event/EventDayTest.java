package circlebinder.common.event;

import android.test.AndroidTestCase;

import circlebinder.common.test.ParcelUtil;

public final class EventDayTest extends AndroidTestCase {

    public void testParcelable() {
        EventDay expect = new EventDayBuilder()
                .setName("今日")
                .build();

        try {
            EventDay got = ParcelUtil.restore(expect);
            assert expect.equals(got);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
