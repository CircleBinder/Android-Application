package circlebinder.common.checklist;

import java.util.List;

import rx.Observer;

final class ChecklistListObserver implements Observer<List<Checklist>> {

    private final ChecklistAdapter adapter;

    public ChecklistListObserver(ChecklistAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(List<Checklist> checklists) {
        adapter.clear();
        adapter.addAll(checklists);
    }
}
