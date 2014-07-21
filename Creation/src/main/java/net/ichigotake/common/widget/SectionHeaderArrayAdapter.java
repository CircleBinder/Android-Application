package net.ichigotake.common.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import circlebinder.creation.R;

public abstract class SectionHeaderArrayAdapter<ITEM, ITEM_TAG, HEADER_TAG>
        extends ArrayAdapter<ITEM, ITEM_TAG>
        implements SectionHeaderAdapter {

    public SectionHeaderArrayAdapter(Context context) {
        super(context);
    }

    @Override
    protected View convertView(int position, View view, ViewGroup parent) {
        final View headerView;
        final HEADER_TAG headerTag;
        final View itemView;
        final ITEM_TAG itemTag;
        final ITEM item = getItem(position);
        if (view == null) {
            view = getLayoutInflater()
                    .inflate(R.layout.circlebinder_section_item_with_header, parent, false);
            headerView = generateHeaderView(position, item, getLayoutInflater(), parent);
            headerTag = generateHeaderTag(position, item, headerView);
            headerView.setTag(headerTag);
            ((ViewGroup)view.findViewById(R.id.circlebinder_section_item_with_header_label))
                    .addView(headerView);

            itemView = generateView(position, item, getLayoutInflater(), (ViewGroup) view);
            itemTag = generateTag(position, item, itemView);
            itemView.setTag(itemTag);
            ((ViewGroup)view.findViewById(R.id.circlebinder_section_item_with_header_item))
                    .addView(itemView);
        } else {
            headerView = ((ViewGroup)view.findViewById(R.id.circlebinder_section_item_with_header_label))
                    .getChildAt(0);
            headerTag = (HEADER_TAG) headerView.getTag();
            itemTag = (ITEM_TAG)((ViewGroup)view.findViewById(R.id.circlebinder_section_item_with_header_item))
                    .getChildAt(0).getTag();
        }

        if (position == 0 || getHeaderId(position) != getHeaderId(position-1)) {
            headerView.setVisibility(View.VISIBLE);
            bindHeaderView(position, item, headerTag);
        } else {
            headerView.setVisibility(View.GONE);
        }
        bindView(position, item, itemTag);
        return view;
    }

    @Override
    public View getHeaderView(int position, View view, ViewGroup viewGroup) {
        HEADER_TAG tag;
        ITEM item = getItem(position);
        if (view == null) {
            view = generateHeaderView(position, item, getLayoutInflater(), viewGroup);
            tag = generateHeaderTag(position, item, view);
            view.setTag(tag);
        } else {
            tag = (HEADER_TAG)view.getTag();
        }

        bindHeaderView(position, item, tag);

        return view;
    }

    @Override
    public long getHeaderId(int position) {
        return getHeaderId(position, getItem(position));
    }

    abstract protected HEADER_TAG generateHeaderTag(int position, ITEM item, View convertView);

    abstract protected View generateHeaderView(int position, ITEM item, LayoutInflater inflater, ViewGroup parent);

    abstract protected void bindHeaderView(int position, ITEM item, HEADER_TAG tag);

    abstract protected long getHeaderId(int position, ITEM item);
}
