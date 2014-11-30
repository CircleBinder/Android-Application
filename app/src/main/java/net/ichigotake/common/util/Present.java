package net.ichigotake.common.util;

import java.util.HashSet;
import java.util.Set;

class Present<T> extends Optional<T> {

    private final T value;

    Present(T value) {
        this.value = value;
    }

    @Override
    public boolean isPresent() {
        return value != null;
    }

    @Override
    public T get() {
        if (value == null) {
            throw new EmptyValueException();
        }
        return value;
    }

    @Override
    public <U> Optional<U> map(Function<? super T, ? extends U> function) {
        return value == null ? new Empty<U>() : new Present<>(function.apply(value));
    }

    @Override
    public <U> Optional<U> flatMap(Function<? super T, Optional<U>> function) {
        return value == null ? new Empty<U>() : function.apply(value);
    }

    @Override
    public T or(T unknown) {
        return value == null ? unknown : value;
    }

    @Override
    public Set<T> asSet() {
        Set<T> set = new HashSet<>();
        if (value != null) {
            set.add(value);
        }
        return set;
    }

}
