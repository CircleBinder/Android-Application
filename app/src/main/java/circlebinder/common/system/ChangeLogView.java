package circlebinder.common.system;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.List;

import circlebinder.R;
import circlebinder.common.changelog.ChangeLogFeed;

public final class ChangeLogView extends FrameLayout {

    private ChangeLogFeedHeaderAdapter adapter;

    @SuppressWarnings("unused") // Public API
    public ChangeLogView(Context context) {
        super(context);
        initialize();
    }

    @SuppressWarnings("unused") // Public API
    public ChangeLogView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    @SuppressWarnings("unused") // Public API
    public ChangeLogView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    private void initialize() {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.common_view_change_log, this, true);
        ListView changeLogsView = (ListView)view.findViewById(R.id.common_view_change_log_list);
        this.adapter = new ChangeLogFeedHeaderAdapter(getContext());
        changeLogsView.setAdapter(adapter);
    }

    public void addChangeLogFeedList(List<ChangeLogFeed> feeds) {
        this.adapter.addAll(feeds);
    }

}
