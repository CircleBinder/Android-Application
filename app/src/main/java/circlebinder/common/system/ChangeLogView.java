package circlebinder.common.system;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ListView;

import net.ichigotake.common.util.Finders;
import net.ichigotake.common.util.ViewFinder;

import java.util.List;

import circlebinder.R;
import circlebinder.common.changelog.ChangeLogFeed;

public final class ChangeLogView extends FrameLayout {

    private final ChangeLogFeedHeaderAdapter adapter;

    @SuppressWarnings("unused") // Public API
    public ChangeLogView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.adapter = new ChangeLogFeedHeaderAdapter(getContext());
        initialize();
    }

    private void initialize() {
        ViewFinder finder = Finders.from(
                LayoutInflater.from(getContext()).inflate(R.layout.common_view_change_log, this, true)
        );
        ListView changeLogsView = finder.findOrNull(R.id.common_view_change_log_list);
        changeLogsView.setAdapter(adapter);
    }

    public void addChangeLogFeedList(List<ChangeLogFeed> feeds) {
        this.adapter.addAll(feeds);
    }

}
