package net.ichigotake.common.widget;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class ArrayAdapter<I, T> extends android.widget.ArrayAdapter<I> {

    private final LayoutInflater inflater;

    public ArrayAdapter(Context context) {
        super(context, 0);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return convertView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return convertView(position, convertView, parent);
     }

    protected View convertView(int position, View convertView, ViewGroup parent) {
        final T tag;
        final I item = getItem(position);
        if (convertView == null) {
            convertView = generateView(position, item, inflater, parent);
            tag = generateTag(position, item, convertView);
            convertView.setTag(tag);
        } else {
            tag = (T)convertView.getTag();
        }

        bindView(position, item, tag);

        return convertView;
    }

    protected LayoutInflater getLayoutInflater() {
        return inflater;
    }

    abstract protected View generateView(int position, I item, LayoutInflater inflater, ViewGroup parent);

    abstract protected void bindView(int position, I item, T tag);

    abstract protected T generateTag(int position, I item, View convertView);

}
