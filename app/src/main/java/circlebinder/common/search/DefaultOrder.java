package circlebinder.common.search;

public final class DefaultOrder implements Order {

    @Override
    public Sequence getSequence() {
        return Sequence.ASC;
    }
}
