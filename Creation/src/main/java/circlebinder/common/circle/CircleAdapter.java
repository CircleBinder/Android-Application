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
import circlebinder.creation.R;

public class CircleAdapter extends CursorAdapter<Circle, CircleViewHolder>
        implements SectionHeaderAdapter {

    private final LayoutInflater inflater;

    public CircleAdapter(Context context, Cursor cursor, CursorItemConverter<Circle> converter) {
        super(context, cursor, converter);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public CircleViewHolder generateTag(int position, Circle item, View convertView) {
        return new CircleViewHolder(convertView);
    }

    @Override
    public View generateView(int position, Circle item, LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.circlebinder_circle_list_item, parent, false);
    }

    @Override
    public void bindView(int position, Circle item, CircleViewHolder tag) {
        tag.getCircleName().setText(item.getName());
        tag.getPenName().setText(item.getPenName());

        tag.getSpace().setCompoundDrawablesWithIntrinsicBounds(
                0,
                item.getChecklistColor().getDrawableResource(),
                0,
                0
        );
        tag.getSpace().setText(
                String.format("%s%02d%s",
                        item.getSpace().getBlockName(), item.getSpace().getNo(), item.getSpace().getNoSub())
        );
        tag.getGenre().setText(item.getGenre().getName());
    }

    @Override
    public View getHeaderView(int i, View view, ViewGroup viewGroup) {
        View headerView = inflater.inflate(R.layout.circlebinder_list_sub_header, viewGroup, false);
        TextView subHeaderView = (TextView) headerView.findViewById(R.id.circlebinder_list_sub_header);
        Circle circle = getItem(i);
        subHeaderView.setText(circle.getSpace().getBlockName());
        return headerView;
    }

    @Override
    public long getHeaderId(int i) {
        return  getItem(i).getSpace().getBlockName().charAt(0);
    }
}
