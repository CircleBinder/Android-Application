package circlebinder.android.app.install;

import java.util.List;

import circlebinder.android.app.service.IServiceCompleted;
import rx.Subscriber;
import rx.functions.Action1;

class OnError implements Action1<List<Throwable>> {

    private final Subscriber<? super IServiceCompleted> subscriber;

    OnError(Subscriber<? super IServiceCompleted> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void call(List<Throwable> throwables) {
        for (Throwable e : throwables) {
            subscriber.onError(e);
        }
    }
}
