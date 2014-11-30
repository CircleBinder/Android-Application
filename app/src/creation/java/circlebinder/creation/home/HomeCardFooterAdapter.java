package circlebinder.creation.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ichigotake.common.util.Finders;
import net.ichigotake.common.widget.SectionHeaderArrayAdapter;
import net.ichigotake.common.widget.SectionHeaderViewHolder;
import net.ichigotake.common.widget.SingleLineTextViewHolder;

import circlebinder.R;
import circlebinder.common.card.HomeCard;

public class HomeCardFooterAdapter
        extends SectionHeaderArrayAdapter<HomeCard, SingleLineTextViewHolder, SectionHeaderViewHolder> {
    
    public HomeCardFooterAdapter(Context context) {
        super(context);
    }

    @Override
    protected SectionHeaderViewHolder generateHeaderTag(int position, HomeCard homeCard, View convertView) {
        return new SectionHeaderViewHolder(Finders.from(convertView));
    }

    @Override
    protected View generateHeaderView(int position, HomeCard homeCard, LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.common_section_sub_header, parent, false);
    }

    @Override
    protected void bindHeaderView(int position, HomeCard homeCard, SectionHeaderViewHolder sectionHeaderViewHolder) {
        sectionHeaderViewHolder.getLabel().setText(homeCard.getLabel());
    }

    @Override
    protected long getHeaderId(int position, HomeCard homeCard) {
        return homeCard.getBackgroundResource();
    }

    @Override
    protected View generateView(int position, HomeCard item, LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.common_list_item, parent, false);
    }

    @Override
    protected void bindView(int position, View convertView, HomeCard item, SingleLineTextViewHolder tag) {
        tag.getTextView().setText(item.getCaption());
    }

    @Override
    protected SingleLineTextViewHolder generateTag(int position, HomeCard item, View convertView) {
        return new SingleLineTextViewHolder(Finders.from(convertView));
    }
}
