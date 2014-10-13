package circlebinder.common.table;

import circlebinder.common.event.EventBlockType;

public final class EventBlockTableForInsert {

    public static class Builder {

        private EventBlockType type;
        private String name;

        public EventBlockTableForInsert build() {
            return new EventBlockTableForInsert(this);
        }

        public Builder setType(EventBlockType type) {
            this.type = type;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }
    }

    private final EventBlockType type;
    private final String name;

    private EventBlockTableForInsert(Builder builder) {
        this.type = builder.type;
        this.name = builder.name;
    }

    int getTypeId() {
        return type.getTypeId();
    }

    String getName() {
        return name;
    }
}
