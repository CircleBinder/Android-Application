package net.ichigotake.common.util;

import java.util.HashSet;
import java.util.Set;

class Empty<T> extends Optional<T> {

    Empty() {
    }

    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public T get() {
        throw new EmptyValueException();
    }

    @Override
    public <U> Optional<U> map(Function<? super T, ? extends U> function) {
        return new Empty<>();
    }

    @Override
    public <U> Optional<U> flatMap(Function<? super T, Optional<U>> function) {
        return new Empty<>();
    }

    @Override
    public T or(T unknown) {
        return unknown;
    }

    @Override
    public Set<T> asSet() {
        return new HashSet<>();
    }

}
