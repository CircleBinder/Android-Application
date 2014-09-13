package net.ichigotake.common.widget;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public abstract class CursorAdapter<ITEM, TAG, HEADER_TAG> extends android.widget.CursorAdapter
        implements ViewBinder<ITEM, TAG>, StickyListHeadersAdapter {

    private final CursorItemConverter<ITEM> converter;
    private final LayoutInflater inflater;

    public CursorAdapter(Context context, Cursor cursor, CursorItemConverter<ITEM> converter) {
        super(context, cursor, false);
        this.inflater = LayoutInflater.from(context);
        this.converter = converter;
    }

    protected LayoutInflater getLayoutInflater() {
        return inflater;
    }

    @Override
    public ITEM getItem(int position) {
        Cursor cursor = (Cursor)super.getItem(position);
        return cursor != null ? converter.create(cursor) : null;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int position = cursor.getPosition();
        ITEM item = converter.create(cursor);
        View itemView = generateView(position, item, inflater, parent);
        itemView.setTag(generateTag(position, item, itemView));
        return itemView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ITEM item = converter.create(cursor);
        int position = cursor.getPosition();
        bindView(position, item, (TAG)view.getTag());
    }

    abstract public View generateView(int position, ITEM item, LayoutInflater inflater, ViewGroup parent);

    abstract public void bindView(int position, ITEM item, TAG tag);

    abstract public TAG generateTag(int position, ITEM item, View convertView);

    abstract protected HEADER_TAG generateHeaderTag(int position, ITEM item, View convertView);

    abstract protected View generateHeaderView(int position, ITEM item, LayoutInflater inflater, ViewGroup parent);

    abstract protected void bindHeaderView(int position, ITEM item, HEADER_TAG tag);

}
