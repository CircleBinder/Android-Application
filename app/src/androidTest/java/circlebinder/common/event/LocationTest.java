package circlebinder.common.event;

import android.test.AndroidTestCase;

import circlebinder.common.test.ParcelUtil;

public final class LocationTest extends AndroidTestCase {

    public void testParcelable() {
        Location expect = new LocationBuilder()
                .setDisplayName("大田区")
                .setLink("scheme://i-am/location/scheme!")
                .build();

        try {
            Location got = ParcelUtil.restore(expect);
            assert expect.getDisplayName().equals(got.getDisplayName());
            assert expect.getLink().equals(got.getLink());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
