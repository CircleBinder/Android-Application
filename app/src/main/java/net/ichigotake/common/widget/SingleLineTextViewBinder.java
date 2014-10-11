package net.ichigotake.common.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import circlebinder.R;

public class SingleLineTextViewBinder implements ViewBinder<String, SingleLineTextViewHolder> {

    @Override
    public SingleLineTextViewHolder generateTag(int position, String item, View convertView) {
        return new SingleLineTextViewHolder((TextView) convertView);
    }

    @Override
    public View generateView(int position, String item, LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.common_list_item, parent, false);
    }

    @Override
    public void bindView(int position, String item, SingleLineTextViewHolder tag) {
        tag.getTextView().setText(item);
    }
}
