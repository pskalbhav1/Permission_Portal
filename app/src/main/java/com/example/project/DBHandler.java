package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "datadb";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "data";
    private static final String NAME_COL = "Name";
    private static final String PSW_COL = "Psw";
    private static final String REG_COL = "Reg";
    private static final String ROLE_COL = "Role";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + REG_COL + " TEXT PRIMARY KEY, "
                + NAME_COL + " TEXT,"
                + PSW_COL + " TEXT,"
                + ROLE_COL + " TEXT)";
        db.execSQL(query);
    }

    public void addNewCourse(String Reg,String Name, String Psw, String Role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(REG_COL, Reg);
        values.put(NAME_COL, Name);
        values.put(PSW_COL, Psw);
        values.put(ROLE_COL, Role);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
