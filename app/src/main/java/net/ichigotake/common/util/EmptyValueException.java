package net.ichigotake.common.util;

public class EmptyValueException extends RuntimeException {

    public EmptyValueException() {
        super("Value must not be null!");
    }

}
