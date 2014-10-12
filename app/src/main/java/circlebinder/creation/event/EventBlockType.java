package circlebinder.creation.event;

public enum EventBlockType {

    //TODO: ネーミング
    一般的なスタイル(1),
    ;

    private final int typeId;

    private EventBlockType(int typeId) {
        this.typeId = typeId;
    }

    public int getTypeId() {
        return typeId;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "  => " + typeId;
    }

    public static EventBlockType get(int blockTypeId) {
        for (EventBlockType item : values()) {
            if (blockTypeId == item.typeId) {
                return item;
            }
        }
        throw new IllegalStateException();
    }
}
