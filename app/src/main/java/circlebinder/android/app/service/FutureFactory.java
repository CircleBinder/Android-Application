package circlebinder.android.app.service;

import java.util.concurrent.Future;

public interface FutureFactory<V> {

    Future<V> createFuture();

}
