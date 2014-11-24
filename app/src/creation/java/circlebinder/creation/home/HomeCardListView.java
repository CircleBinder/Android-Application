package circlebinder.creation.home;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;

import circlebinder.R;
import circlebinder.common.card.HomeCard;
import circlebinder.common.card.HomeCardPresenter;

public final class HomeCardListView extends FrameLayout {

    private final HomeCardPresenter presenter;

    @SuppressWarnings("unused") // Public API
    public HomeCardListView(Context context) {
        super(context);
        this.presenter = new HomeCardPresenter(getContext());
    }

    @SuppressWarnings("unused") // Public API
    public HomeCardListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.presenter = new HomeCardPresenter(getContext());
    }

    @SuppressWarnings("unused") // Public API
    public HomeCardListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.presenter = new HomeCardPresenter(getContext());
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            headerView.setBackgroundResource(R.drawable.common_ripple);
            ViewCompat.setElevation(headerView, 10);
        }
        GridView checklistsView = (GridView)container.findViewById(R.id.creation_view_checklist_list);
        checklistsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.itemClicked((HomeCard) parent.getItemAtPosition(position));
            }
        });
        presenter.listViewAttached(checklistsView);
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
