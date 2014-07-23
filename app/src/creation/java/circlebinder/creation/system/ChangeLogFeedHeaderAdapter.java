package circlebinder.creation.system;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.ichigotake.common.widget.SectionHeaderArrayAdapter;

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
        return new SectionHeaderViewHolder((TextView) convertView.findViewById(R.id.common_section_sub_header));
    }

    @Override
    protected View generateHeaderView(int position, ChangeLogFeed changeLogFeed, LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.common_section_sub_header, parent, false);
    }

    @Override
    protected void bindHeaderView(int position, ChangeLogFeed item, SectionHeaderViewHolder tag) {
        String label = "バージョン" + item.getVersionName() + " " + item.getPublishDate().getFormattedDate();
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
    protected void bindView(int position, ChangeLogFeed item, ChangeLogFeedViewHolder tag) {
        tag.getLabel().setText(item.getTitle());
    }

    @Override
    protected ChangeLogFeedViewHolder generateTag(int position, ChangeLogFeed item, View convertView) {
        return new ChangeLogFeedViewHolder(convertView);
    }
}
