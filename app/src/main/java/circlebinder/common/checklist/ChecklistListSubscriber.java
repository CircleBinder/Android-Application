package circlebinder.common.checklist;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

final class ChecklistListSubscriber implements Observable.OnSubscribe<List<Checklist>> {

    @Override
    public void call(Subscriber<? super List<Checklist>> subscriber) {
        try {
            List<Checklist> checklists = new ChecklistListCallable().call();
            subscriber.onNext(checklists);
            subscriber.onCompleted();
        } catch (Exception e) {
            subscriber.onError(e);
        }
    }
}
