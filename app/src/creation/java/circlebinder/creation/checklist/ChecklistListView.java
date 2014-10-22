package circlebinder.creation.checklist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.felipecsl.abslistviewhelper.library.AbsListViewHelper;

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
        View headerView = container.findViewById(R.id.creation_view_checklist_list_header);
        headerView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.headerClicked();
            }
        });
        AbsListView checklistsView = (AbsListView) container.findViewById(R.id.create_view_checklist_list);
        presenter.listViewAttached(checklistsView);
        new AbsListViewHelper(checklistsView)
                .setHeaderView(headerView);
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
