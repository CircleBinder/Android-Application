package circlebinder.common.table;

public final class EventCircleTableForInsert {

    public static class Builder {
        private long blockId;
        private int spaceNo;
        private int spaceNoSub;
        private String circleName;
        private String penName;
        private String homepage;
        private int checklistId;

        public EventCircleTableForInsert build() {
            return new EventCircleTableForInsert(this);
        }

        public Builder setSpaceNo(int spaceNo) {
            this.spaceNo = spaceNo;
            return this;
        }

        public Builder setBlockId(long blockId) {
            this.blockId = blockId;
            return this;
        }

        public Builder setSpaceNoSub(int spaceNoSub) {
            this.spaceNoSub = spaceNoSub;
            return this;
        }

        public Builder setCircleName(String circleName) {
            this.circleName = circleName;
            return this;
        }

        public Builder setPenName(String penName) {
            this.penName = penName;
            return this;
        }

        public Builder setHomepage(String homepage) {
            this.homepage = homepage;
            return this;
        }

        public Builder setChecklistId(int checklistId) {
            this.checklistId = checklistId;
            return this;
        }

    }

    private final long blockId;
    private final int spaceNo;
    private final int spaceNoSub;
    private final String circleName;
    private final String penName;
    private final String homepage;
    private final int checklistId;

    private EventCircleTableForInsert(Builder builder) {
        this.blockId = builder.blockId;
        this.spaceNo = builder.spaceNo;
        this.spaceNoSub = builder.spaceNoSub;
        this.circleName = builder.circleName;
        this.penName = builder.penName;
        this.homepage = builder.homepage;
        this.checklistId = builder.checklistId;
    }

    long getBlockId() {
        return blockId;
    }

    int getSpaceNo() {
        return spaceNo;
    }

    int getSpaceNoSub() {
        return spaceNoSub;
    }

    String getCircleName() {
        return circleName;
    }

    String getPenName() {
        return penName;
    }

    String getHomepage() {
        return homepage;
    }

    int getChecklistId() {
        return checklistId;
    }
}
