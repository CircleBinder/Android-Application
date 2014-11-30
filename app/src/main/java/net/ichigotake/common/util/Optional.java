package net.ichigotake.common.util;

import java.util.Set;

public abstract class Optional<T> {

    public static <T> Optional<T> empty() {
        return new Empty<>();
    }

    public static <T> Optional<T> of(T value) {
        if (value == null) {
            throw new EmptyValueException();
        }
        return new Present<>(value);
    }

    public static <T> Optional<T> fromNullable(T value) {
        return value == null ? new Empty<T>() : new Present<>(value);
    }

    public abstract boolean isPresent();

    public abstract T get();

    public abstract <U> Optional<U> map(Function<? super T, ? extends U> function);

    public abstract <U> Optional<U> flatMap(Function<? super T, Optional<U>> function);

    public abstract Set<T> asSet();

    public abstract T or(T unknown);

}
