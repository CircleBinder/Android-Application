package circlebinder.common.system;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.ichigotake.common.util.Finders;
import net.ichigotake.common.widget.SectionHeaderArrayAdapter;
import net.ichigotake.common.widget.SectionHeaderViewHolder;

import circlebinder.common.changelog.ChangeLogFeed;
import circlebinder.common.changelog.ChangeLogFeedViewHolder;
import circlebinder.R;

public final class ChangeLogFeedHeaderAdapter
        extends SectionHeaderArrayAdapter<ChangeLogFeed, ChangeLogFeedViewHolder, SectionHeaderViewHolder> {

    public ChangeLogFeedHeaderAdapter(Context context) {
        super(context);
    }

    @Override
    protected SectionHeaderViewHolder generateHeaderTag(int position, ChangeLogFeed item, View convertView) {
        return new SectionHeaderViewHolder(Finders.from(convertView).find(R.id.common_section_sub_header));
    }

    @Override
    public View generateHeaderView(int position, ChangeLogFeed changeLogFeed, LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.common_section_sub_header, parent, false);
    }

    @Override
    protected void bindHeaderView(int position, ChangeLogFeed item, SectionHeaderViewHolder tag) {
        String label = getContext().getString(R.string.common_change_log_publish_date_format,
                item.getPublishDate().getFormattedDate(), item.getVersionName());
        tag.getLabel().setText(label);
    }

    @Override
    protected long getHeaderId(int position, ChangeLogFeed item) {
        return item.getVersionCode();
    }

    @Override
    protected View generateView(int position, ChangeLogFeed item, LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.common_list_item, parent, false);
    }

    @Override
    protected void bindView(int position, View convertView, ChangeLogFeed item, ChangeLogFeedViewHolder tag) {
        tag.getLabel().setText(item.getTitle());
    }

    @Override
    protected ChangeLogFeedViewHolder generateTag(int position, ChangeLogFeed item, View convertView) {
        return new ChangeLogFeedViewHolder(convertView);
    }
}
