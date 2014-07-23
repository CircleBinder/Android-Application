package net.ichigotake.common.widget;

import android.database.Cursor;

public interface CursorItemConverter<ITEM> {

    ITEM create(Cursor cursor);
}
