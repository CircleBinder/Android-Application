package net.ichigotake.common.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 * @param <I> 一要素
 * @param <T> ビューに埋め込むタグ
 */
public interface ViewBinder<I, T> {

    T generateTag(int position, I item, View convertView);

    View generateView(int position, I item, LayoutInflater inflater, ViewGroup parent);

    void bindView(int position, I item, T tag);
}
