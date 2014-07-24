package circlebinder.common.event;

import android.test.AndroidTestCase;

import circlebinder.common.test.ParcelUtil;

public final class NearbyStationTest extends AndroidTestCase {

    public void testParcelable() {
        NearbyStation expect = new NearbyStationBuilder()
                .setDisplay("最寄り駅")
                .build();

        try {
            NearbyStation got = ParcelUtil.restore(expect);
            assert expect.getDisplay().equals(got.getDisplay());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
