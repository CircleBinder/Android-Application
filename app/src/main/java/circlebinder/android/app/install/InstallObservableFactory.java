package circlebinder.android.app.install;

import android.content.res.Resources;

import java.util.List;

import circlebinder.android.app.Logger;
import circlebinder.android.app.R;
import circlebinder.android.app.service.IServiceCompleted;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class InstallObservableFactory {

    private final String TAG = InstallObservableFactory.class.getSimpleName();
    private final Resources resources;

    public InstallObservableFactory(Resources resources) {
        this.resources = resources;
    }

    public Observable<IServiceCompleted> createObservable() {
        return Observable.create(new Observable.OnSubscribe<IServiceCompleted>() {
            @Override
            public void call(final Subscriber<? super IServiceCompleted> subscriber) {
                FileReader fileReader = new FileReader(resources);
                OnError onError = new OnError(subscriber);

                Result<List<String>> blocksResult = fileReader.parse(R.raw.creation_blocks_ltsv, new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s;
                    }
                }).either(onError, new Action1<List<String>>() {
                    @Override
                    public void call(List<String> strings) {
                        for (String l : strings) {
                            Logger.debug(TAG, l);
                        }
                    }
                });
                Result<List<String>> circlesResult = fileReader.parse(R.raw.creation_circles_ltsv, new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s;
                    }
                }).either(onError, new Action1<List<String>>() {
                    @Override
                    public void call(List<String> strings) {
                        for (String l : strings) {
                            Logger.debug(TAG, l);
                        }
                    }
                });
                Result.mergeError("All succeeded!", blocksResult, circlesResult)
                        .either(onError, new Action1<String>() {
                            @Override
                            public void call(String s) {
                                subscriber.onNext(new IServiceCompleted(s));
                            }
                        });
            }
        });
    }
}
