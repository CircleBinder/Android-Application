package circlebinder.android.app.install;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

class Result<T> {

    static <T> Result<T> mergeError(T rightEvent, Result<?>... results) {
        List<Throwable> throwables = new ArrayList<>();
        for (Result<?> r : results) {
            throwables.addAll(r.throwables);
        }
        return new Result<>(throwables, rightEvent);
    }

    private final T rightEvent;
    private final List<Throwable> throwables;

    Result(@NonNull List<Throwable> throwables, @NonNull T rightEvent) {
        this.rightEvent = rightEvent;
        this.throwables = throwables;
    }

    Result<T> either(Action1<List<Throwable>> left, Action1<T> right) {
        if (!throwables.isEmpty()) {
            left.call(throwables);
            return this;
        }
        right.call(rightEvent);
        return this;
    }

}
