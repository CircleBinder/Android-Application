package circlebinder.common.changelog;

final class MockPublishDate {

    static PublishDate create() {
        return new PublishDate.Builder()
                .setFormattedDate("2013-04-05")
                .setTimestamp(100)
                .build();
    }
}
