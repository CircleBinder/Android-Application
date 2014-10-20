package circlebinder.creation.checklist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import circlebinder.R;
import circlebinder.common.checklist.Checklist;
import circlebinder.common.checklist.ChecklistListPresenter;

public final class ChecklistListView extends FrameLayout {

    private final ChecklistListPresenter presenter;

    @SuppressWarnings("unused") // Public API
    public ChecklistListView(Context context) {
        super(context);
        this.presenter = new ChecklistListPresenter(getContext());
    }

    @SuppressWarnings("unused") // Public API
    public ChecklistListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.presenter = new ChecklistListPresenter(getContext());
    }

    @SuppressWarnings("unused") // Public API
    public ChecklistListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.presenter = new ChecklistListPresenter(getContext());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View container = inflater.inflate(R.layout.creation_view_checklist_list, this, true);
        View headerView = inflater.inflate(R.layout.creation_enjoy_creation_search_guidance, this, false);
        ListView checklistsView = (ListView) container.findViewById(R.id.create_view_checklist_list);
        presenter.listViewAttached(checklistsView);
        checklistsView.addHeaderView(headerView, null, false);
        checklistsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.itemClicked((Checklist) parent.getItemAtPosition(position));
            }
        });
        reload();
    }

    public void reload() {
        presenter.reload();
    }

    @Override
    protected void onDetachedFromWindow() {
        presenter.destroy();
        super.onDetachedFromWindow();
    }

}
