package net.ichigotake.common.rx.content;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import rx.Observable;
import rx.android.observables.AndroidObservable;

public class ObservableBinder {

    private static final String TAG = ObservableBinder.class.getSimpleName();

    public static <T> Observable<T> maybeBind(Context context, Observable<T> observable) {
        if (context instanceof Activity) {
            Log.d(TAG, String.format("Bound %s to %s.",
                    observable.getClass().getSimpleName(), context.getClass().getSimpleName()));
            return AndroidObservable.bindActivity((Activity) context, observable);
        }
        Log.d(TAG, String.format("Did not bind %s to %s.",
                observable.getClass().getSimpleName(), context.getClass().getSimpleName()));
        return observable;
    }

}
