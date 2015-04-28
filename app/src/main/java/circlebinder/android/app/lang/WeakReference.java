package circlebinder.android.app.lang;

import java.lang.ref.ReferenceQueue;
import java.util.HashSet;
import java.util.Set;

public class WeakReference<T> extends java.lang.ref.WeakReference<T> {

    public WeakReference(T r) {
        super(r);
    }

    public WeakReference(T r, ReferenceQueue<T> q) {
        super(r, q);
    }

    public Set<T> asSet() {
        Set<T> set = new HashSet<>();
        if (!enqueue()) {
            set.add(get());
        }
        return set;
    }
}
