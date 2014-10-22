package circlebinder.common.checklist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;

import net.ichigotake.common.app.ActivityTripper;

import circlebinder.common.app.phone.ChecklistActivity;
import circlebinder.common.app.phone.CircleSearchActivity;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public final class ChecklistListPresenter {

    private final Context context;
    private final ChecklistAdapter adapter;
    private Subscription subscriptions;

    public ChecklistListPresenter(Context context) {
        this.context = context;
        this.adapter = new ChecklistAdapter(context);
    }

    public void listViewAttached(AbsListView listVIew) {
        listVIew.setAdapter(this.adapter);
    }

    public void itemClicked(Checklist item) {
        Intent nextIntent = ChecklistActivity.createIntent(context, item.getChecklistColor());
        new ActivityTripper(context, nextIntent).trip();
    }

    public void reload() {
        destroy();
        subscriptions = Observable.create(new ChecklistListSubscriber())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ChecklistListObserver(adapter));
    }

    public void destroy() {
        if (subscriptions != null && subscriptions.isUnsubscribed()) {
            subscriptions.unsubscribe();
        }
    }

    public void headerClicked() {
        new ActivityTripper(context, CircleSearchActivity.createIntent(context)).trip();
    }
}
