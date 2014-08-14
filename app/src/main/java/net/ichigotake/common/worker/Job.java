package net.ichigotake.common.worker;

public interface Job<T> {

    void run(T value);

}
