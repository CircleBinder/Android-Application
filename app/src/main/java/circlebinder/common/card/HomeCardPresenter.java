package circlebinder.common.card;

import android.content.Context;
import android.widget.AbsListView;

import net.ichigotake.common.app.ActivityTripper;

import java.util.ArrayList;
import java.util.List;

import circlebinder.common.app.phone.CircleSearchActivity;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public final class HomeCardPresenter {

    private final Context context;
    private final HomeCardAdapter adapter;
    private final List<HomeCard> cards;
    private Subscription subscriptions;

    public HomeCardPresenter(Context context) {
        this.context = context;
        this.adapter = new HomeCardAdapter(context);
        this.cards = new ArrayList<>();
    }

    public void listViewAttached(AbsListView listVIew) {
        listVIew.setAdapter(this.adapter);
    }

    public void itemClicked(HomeCard item) {
        new ActivityTripper(context, item.createTransitIntent(context)).trip();
    }

    public void reload() {
        destroy();
        subscriptions = Observable.create(new HomeCardSubscriber(cards))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HomeCardObserver(adapter));
    }

    public void destroy() {
        if (subscriptions != null && !subscriptions.isUnsubscribed()) {
            subscriptions.unsubscribe();
        }
    }

    public void headerClicked() {
        new ActivityTripper(context, CircleSearchActivity.createIntent(context)).trip();
    }

    public void addItem(HomeCard item) {
        cards.add(item);
        adapter.notifyDataSetChanged();
    }
}
