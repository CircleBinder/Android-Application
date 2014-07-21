package net.ichigotake.common.database;

import android.database.Cursor;

/**
 * API level 1
 * 
 * Wrapper class for {@link android.database.Cursor}.
 */
public class CursorSimple {

    final private Cursor mCursor;

    /**
     * API level 1
     *
     * Constructs from {@link android.database.Cursor}.
     * @param c
     */
    public CursorSimple(Cursor c) {
        mCursor = c;
    }
    
    /**
     * API level 1
     * 
     * Move the cursor to the first row.
     * 
     * @return
     */
    public boolean moveToFirst() {
        return mCursor.moveToFirst();
    }
    
    /**
     * API level 1
     * 
     * Move the cursor to the next row.
     * 
     * @return
     */
    public boolean moveToNext() {
        return mCursor.moveToNext();
    }
    
    /**
     * API level 1
     * 
     * Returns the value of the requested column as a byte array.
     * 
     * @param coloumn
     * @return
     */
    public byte[] getBlob(String coloumn) {
        return mCursor.getBlob(mCursor.getColumnIndex(coloumn));
    }

    /**
     * API level 1
     * 
     * Returns the value of the requested column as an int.
     * 
     * @param coloumn
     * @return
     */
    public int getInt(String coloumn) {
        return mCursor.getInt(mCursor.getColumnIndex(coloumn));
    }

    /**
     * API level 1
     * 
     * Returns the value of the requested column as a long.
     * 
     * @param coloumn
     * @return
     */
    public long getLong(String coloumn) {
        return mCursor.getLong(mCursor.getColumnIndex(coloumn));
    }

    /**
     * API level 1
     * 
     * Returns the value of the requested column as an {@link String}.
     * 
     * @param coloumn
     * @return
     */
    public String getString(String coloumn) {
        return mCursor.getString(mCursor.getColumnIndex(coloumn));
    }

    /**
     * API level 1
     * 
     * Closes the Cursor, releasing all of its resources and making it completely invalid.
     */
    public void close() {
        mCursor.close();
    }
}
