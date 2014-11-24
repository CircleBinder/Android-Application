package circlebinder.common.card;

import java.util.List;

import circlebinder.common.checklist.Checklist;
import rx.Observable;
import rx.Subscriber;

final class HomeCardSubscriber implements Observable.OnSubscribe<List<HomeCard>> {

    @Override
    public void call(Subscriber<? super List<HomeCard>> subscriber) {
        try {
            List<HomeCard> checklists = new ChecklistCardCallable().call();
            subscriber.onNext(checklists);
            subscriber.onCompleted();
        } catch (Exception e) {
            subscriber.onError(e);
        }
    }
}
