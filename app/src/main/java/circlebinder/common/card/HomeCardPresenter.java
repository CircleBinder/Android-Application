package circlebinder.common.card;

import android.content.Context;
import android.widget.AbsListView;

import net.ichigotake.common.app.ActivityTripper;

import circlebinder.common.app.phone.CircleSearchActivity;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public final class HomeCardPresenter {

    private final Context context;
    private final HomeCardAdapter adapter;
    private Subscription subscriptions;

    public HomeCardPresenter(Context context) {
        this.context = context;
        this.adapter = new HomeCardAdapter(context);
    }

    public void listViewAttached(AbsListView listVIew) {
        listVIew.setAdapter(this.adapter);
    }

    public void itemClicked(HomeCard item) {
        new ActivityTripper(context, item.createTransitIntent(context)).trip();
    }

    public void reload() {
        destroy();
        subscriptions = Observable.create(new HomeCardSubscriber())
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

}
