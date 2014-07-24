package net.ichigotake.common.widget;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class CursorAdapter<ITEM, TAG> extends android.widget.CursorAdapter
        implements ViewBinder<ITEM, TAG> {

    private final CursorItemConverter<ITEM> converter;
    private final LayoutInflater inflater;

    public CursorAdapter(Context context, Cursor cursor, CursorItemConverter<ITEM> converter) {
        super(context, cursor, false);
        this.inflater = LayoutInflater.from(context);
        this.converter = converter;
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
        View newView = generateView(position, item, inflater, parent);
        newView.setTag(generateTag(position, item, newView));
        return newView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TAG tag = (TAG)view.getTag();
        ITEM item = converter.create(cursor);
        int position = cursor.getPosition();
        bindView(position, item, tag);
    }

    abstract public View generateView(int position, ITEM item, LayoutInflater inflater, ViewGroup parent);

    abstract public void bindView(int position, ITEM item, TAG tag);

    abstract public TAG generateTag(int position, ITEM item, View convertView);

}
