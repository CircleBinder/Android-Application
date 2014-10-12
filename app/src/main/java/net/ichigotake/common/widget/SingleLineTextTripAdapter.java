package net.ichigotake.common.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ichigotake.common.app.OnClickToTrip;
import net.ichigotake.common.app.Tripper;

public final class SingleLineTextTripAdapter extends ArrayAdapter<ListItemTrip<String>, SingleLineTextViewHolder> {

    private final SingleLineTextViewBinder binder;
    private final ListItemTripFactory<String> itemFactory;

    public SingleLineTextTripAdapter(Context context) {
        super(context);
        this.binder = new SingleLineTextViewBinder();
        this.itemFactory = new ListItemTripFactory<>();
    }

    public void add(String label, Tripper tripper) {
        add(itemFactory.create(label, tripper));
    }

    @Override
    protected View generateView(int position, ListItemTrip<String> item, LayoutInflater inflater, ViewGroup parent) {
        return this.binder.generateView(position, item.getItem(), inflater, parent);
    }

    @Override
    protected void bindView(int position, ListItemTrip<String> item, SingleLineTextViewHolder tag) {
        this.binder.bindView(position, item.getItem(), tag);
        tag.getTextView().setOnClickListener(new OnClickToTrip(item.getTripper()));
    }

    @Override
    protected SingleLineTextViewHolder generateTag(int position, ListItemTrip<String> item, View convertView) {
        return this.binder.generateTag(position, item.getItem(), convertView);
    }

}
