package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBSlot extends SQLiteOpenHelper {
    private static final String DB_NAME = "datadb1";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "data";
    private static final String ROOM_No = "Room_No";
    private static final String DATE = "Date";
    private static final String TIME = "Time";
    private static final boolean BOOKED = false;

    public DBSlot(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public DBSlot(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ROOM_No + " TEXT PRIMARY KEY, "
                + DATE + " TEXT,"
                + TIME + " TEXT,"
                + BOOKED + " boolean)";
        db.execSQL(query);
    }

    public void addNewCourse(String Room_No,String Date, String Time, boolean booked) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ROOM_No,Room_No);
        values.put(DATE,Date);
        values.put(TIME,Time);
        values.put(String.valueOf(BOOKED),booked);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
