package net.ichigotake.common.util;

import java.util.ArrayList;
import java.util.List;

public class MonadList<T> {

    public final List<T> list;

    public MonadList(List<T> list) {
        this.list = list;
    }

    public MonadList<T> filter(Function<T, Boolean> function) {
        List<T> r = new ArrayList<>();
        for (T t : list) {
            if (function.apply(t)) {
                r.add(t);
            }
        }
        return new MonadList<>(r);
    }
}
