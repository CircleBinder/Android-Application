package circlebinder.common.search;

public enum CircleOrder implements Order {

    CIRCLE_NAME_ASC(Sequence.ASC),
    CIRCLE_SPACE_ASC(Sequence.ASC),
    CHECKLIST(Sequence.ASC),
    DEFAULT(Sequence.ASC),
    ;

    private final Sequence sequence;

    private CircleOrder(Sequence sequence) {
        this.sequence = sequence;
    }

    @Override
    public Sequence getSequence() {
        return sequence;
    }

    public static CircleOrder get(String value) {
        CircleOrder got = DEFAULT;
        for (CircleOrder order : values()) {
            if (order.name().equals(value)) {
                got = order;
            }
        }
        return got;
    }
}
