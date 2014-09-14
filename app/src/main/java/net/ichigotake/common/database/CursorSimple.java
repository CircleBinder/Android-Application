package net.ichigotake.common.database;

import android.database.Cursor;

public class CursorSimple {

    final private Cursor mCursor;

    public CursorSimple(Cursor c) {
        mCursor = c;
    }
    
    public boolean moveToFirst() {
        return mCursor.moveToFirst();
    }
    
    public boolean moveToNext() {
        return mCursor.moveToNext();
    }
    
    public byte[] getBlob(String coloumn) {
        return mCursor.getBlob(mCursor.getColumnIndex(coloumn));
    }

    public int getInt(String coloumn) {
        return mCursor.getInt(mCursor.getColumnIndex(coloumn));
    }

    public long getLong(String coloumn) {
        return mCursor.getLong(mCursor.getColumnIndex(coloumn));
    }

    public String getString(String coloumn) {
        return mCursor.getString(mCursor.getColumnIndex(coloumn));
    }

    public void close() {
        mCursor.close();
    }
}
