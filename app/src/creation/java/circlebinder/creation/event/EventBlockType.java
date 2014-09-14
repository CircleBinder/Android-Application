package circlebinder.creation.event;

public enum EventBlockType {

    //TODO: ネーミング
    一般的なスタイル(1),
    ;

    private final int typeId;

    private EventBlockType(int typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "  => " + typeId;
    }

}
