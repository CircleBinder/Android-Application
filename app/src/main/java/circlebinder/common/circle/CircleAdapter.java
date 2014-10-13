package circlebinder.common.circle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.ichigotake.common.widget.CursorAdapter;
import net.ichigotake.common.widget.CursorItemConverter;

import circlebinder.common.event.Circle;
import circlebinder.R;
import circlebinder.creation.system.SectionHeaderViewHolder;

public final class CircleAdapter extends CursorAdapter<Circle, CircleViewHolder, SectionHeaderViewHolder> {

    public CircleAdapter(Context context, CursorItemConverter<Circle> converter) {
        super(context, null, converter);
    }

    @Override
    public CircleViewHolder generateTag(int position, Circle item, View convertView) {
        return new CircleViewHolder(convertView);
    }

    @Override
    protected SectionHeaderViewHolder generateHeaderTag(int position, Circle circle, View convertView) {
        return new SectionHeaderViewHolder((TextView)convertView);
    }

    @Override
    protected View generateHeaderView(int position, Circle circle, LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(SectionHeaderViewHolder.layoutResourceId, parent, false);
    }

    @Override
    protected void bindHeaderView(int position, Circle circle, SectionHeaderViewHolder holder) {
        holder.getLabel().setText(circle.getSpace().getBlockName());
    }

    @Override
    public View generateView(int position, Circle item, LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.common_circle_list_item, parent, false);
    }

    @Override
    public void bindView(final int position, Circle item, CircleViewHolder tag) {
        tag.getCircleName().setText(item.getName());
        tag.getPenName().setText(item.getPenName());

        tag.getChecklistSelector().setCircle(item);
        tag.getChecklistSelector().setPopupAnchor(tag.getSpaceContainer());
        tag.getSpaceContainer().setOnClickListener((v) -> tag.getChecklistSelector().showPopup());
        tag.getSpace().setText(
                String.format("%s%02d%s",
                        item.getSpace().getBlockName(), item.getSpace().getNo(), item.getSpace().getNoSub())
        );
        tag.getGenre().setText(item.getGenre().getName());
    }

    @Override
    public View getHeaderView(int position, View view, ViewGroup viewGroup) {
        SectionHeaderViewHolder tag;
        Circle item = getItem(position);
        if (view == null) {
            view = generateHeaderView(position, item, getLayoutInflater(), viewGroup);
            tag = generateHeaderTag(position, item, view);
            view.setTag(tag);
        } else {
            tag = (SectionHeaderViewHolder)view.getTag();
        }

        bindHeaderView(position, item, tag);

        return view;
    }

    @Override
    public long getHeaderId(int i) {
        return getItem(i).getSpace().getBlockName().charAt(0);
    }

    //TODO: メインスレッドでリロード処理をしないようにする
    public void reload() {
        getCursor().requery();
    }
}
