package circlebinder.common.card;

import android.content.Context;
import android.widget.AbsListView;

import net.ichigotake.common.app.ActivityTripper;
import net.ichigotake.common.rx.content.ObservableBinder;
import net.ichigotake.common.util.Optional;

import circlebinder.common.app.phone.CircleSearchActivity;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public final class HomeCardPresenter {

    private final Context context;
    private final HomeCardAdapter adapter;
    private Optional<Subscription> subscription = Optional.empty();

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
        subscription = Optional.of(ObservableBinder.maybeBind(context, Observable.create(new HomeCardSubscriber()))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HomeCardObserver(adapter))
        );
    }

    public void destroy() {
        for (Subscription subscribedTask : subscription.asSet()) {
            if (!subscribedTask.isUnsubscribed()) {
                subscribedTask.unsubscribe();
            }
        }
    }

    public void headerClicked() {
        new ActivityTripper(context, CircleSearchActivity.createIntent(context)).trip();
    }

}
