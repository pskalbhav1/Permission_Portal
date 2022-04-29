package com.example.project;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.support.annotation.Nullable;

public class DBRequest extends SQLiteOpenHelper {
    private static final String DB_NAME = "datadb2";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "data2";
    private static final String TO_ID = "ID";
    private static final String SUBJECT = "Subject";
    private static final String MESSAGE = "Message";
    private static final boolean STATUS=false;

    public DBRequest(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public DBRequest(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + TO_ID + " TEXT PRIMARY KEY, "
                + SUBJECT + " TEXT,"
                + MESSAGE+ " TEXT,"
                + STATUS+ "true)";
        db.execSQL(query);
    }

    public void addNewCourse(String To_ID,String Subject, String Message, boolean Status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TO_ID,To_ID);
        values.put(SUBJECT,Subject);
        values.put(MESSAGE,Message);
        values.put(String.valueOf(STATUS), Status);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
