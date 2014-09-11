package circlebinder.common.circle;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.ichigotake.common.widget.CursorAdapter;
import net.ichigotake.common.widget.CursorItemConverter;
import net.ichigotake.common.widget.SectionHeaderAdapter;

import circlebinder.common.event.Circle;
import circlebinder.R;

public final class CircleAdapter extends CursorAdapter<Circle, CircleViewHolder>
        implements SectionHeaderAdapter {

    private final LayoutInflater inflater;
    private final OnCircleItemClickListener onCircleItemClickListener;

    public CircleAdapter(
            Context context,
            CursorItemConverter<Circle> converter,
            OnCircleItemClickListener onCircleItemClickListener
    ) {
        super(context, null, converter);
        this.inflater = LayoutInflater.from(context);
        this.onCircleItemClickListener = onCircleItemClickListener;
    }

    @Override
    public CircleViewHolder generateTag(int position, Circle item, View convertView) {
        return new CircleViewHolder(convertView);
    }

    @Override
    public View generateView(int position, Circle item, LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.common_circle_list_item, parent, false);
    }

    @Override
    public void bindView(final int position, final Circle item, final CircleViewHolder tag) {
        tag.getCircleName().setText(item.getName());
        tag.getPenName().setText(item.getPenName());

        tag.getSpaceContainer().setOnClickListener(v ->
                onCircleItemClickListener.onSpaceClick(tag, position, item));
        tag.getChecklist().setImageResource(item.getChecklistColor().getDrawableResource());
        tag.getSpace().setText(
                String.format("%s%02d%s",
                        item.getSpace().getBlockName(), item.getSpace().getNo(), item.getSpace().getNoSub())
        );
        tag.getGenre().setText(item.getGenre().getName());
    }

    @Override
    public View getHeaderView(int i, View view, ViewGroup viewGroup) {
        View headerView = inflater.inflate(R.layout.common_section_sub_header, viewGroup, false);
        TextView subHeaderView = (TextView) headerView.findViewById(R.id.common_section_sub_header);
        Circle circle = getItem(i);
        subHeaderView.setText(circle.getSpace().getBlockName());
        return headerView;
    }

    @Override
    public long getHeaderId(int i) {
        return  getItem(i).getSpace().getBlockName().charAt(0);
    }

    /**
     * TODO: {@link Cursor#requery} はDeprecated
     */
    public void reload() {
        getCursor().requery();
    }
}
