package circlebinder.common.card;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

final class HomeCardSubscriber implements Observable.OnSubscribe<List<HomeCard>> {

    private final List<HomeCard> cards;

    HomeCardSubscriber(List<HomeCard> cards) {
        this.cards = cards;
    }

    @Override
    public void call(Subscriber<? super List<HomeCard>> subscriber) {
        try {
            cards.addAll(0, new ChecklistCardCallable().call());
            subscriber.onNext(cards);
            subscriber.onCompleted();
        } catch (Exception e) {
            subscriber.onError(e);
        }
    }
}
