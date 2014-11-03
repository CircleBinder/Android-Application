package circlebinder.common.card;

import java.util.List;

import rx.Observer;

final class HomeCardObserver implements Observer<List<HomeCard>> {

    private final HomeCardAdapter adapter;

    public HomeCardObserver(HomeCardAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(List<HomeCard> checklists) {
        adapter.clear();
        adapter.addAll(checklists);
    }
}
