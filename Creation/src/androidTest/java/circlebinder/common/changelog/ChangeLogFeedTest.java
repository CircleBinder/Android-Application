package circlebinder.common.changelog;

import android.test.AndroidTestCase;

import circlebinder.common.test.ParcelUtil;

public final class ChangeLogFeedTest extends AndroidTestCase {

    public void testParcelable() {
        int versionCode = 2;
        String versionName = "0.2";
        ChangeLogFeedType type = ChangeLogFeedType.BUG;
        PublishDate publishDate = MockPublishDate.create();
        String title = "不具合を修正しました";
        ChangeLogFeed feed = new ChangeLogFeed(
                versionCode,
                versionName,
                type,
                publishDate,
                title
        );

        assertEquals(versionCode, feed.getVersionCode());
        assertEquals(versionName, feed.getVersionName());
        assertEquals(title, feed.getTitle());
        assertEquals(type, feed.getType());
        assertEquals(publishDate.getFormattedDate(), feed.getPublishDate().getFormattedDate());
        assertEquals(publishDate.getTimestamp(), feed.getPublishDate().getTimestamp());

        try {
            ChangeLogFeed restoredFeed = ParcelUtil.restore(feed);

            assertEquals(feed.getVersionCode(), restoredFeed.getVersionCode());
            assertEquals(feed.getVersionName(), restoredFeed.getVersionName());
            assertEquals(feed.getTitle(), restoredFeed.getTitle());
            assertEquals(feed.getType(), restoredFeed.getType());
            assertEquals(feed.getPublishDate().getFormattedDate(), restoredFeed.getPublishDate().getFormattedDate());
            assertEquals(feed.getPublishDate().getTimestamp(), restoredFeed.getPublishDate().getTimestamp());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
