package circlebinder.common.card;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

final class HomeCardSubscriber implements Observable.OnSubscribe<List<HomeCard>> {

    private final List<HomeCard> eventCards;

    HomeCardSubscriber(List<HomeCard> cards) {
        this.eventCards = cards;
    }

    @Override
    public void call(Subscriber<? super List<HomeCard>> subscriber) {
        try {
            eventCards.addAll(0, new ChecklistCardCallable().call());
            subscriber.onNext(eventCards);
            subscriber.onCompleted();
        } catch (Exception e) {
            subscriber.onError(e);
        }
    }
}
