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
    private static final String STATUS="0";

    public DBRequest(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public DBRequest(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + TO_ID + " TEXT , "
                + SUBJECT + " TEXT PRIMARY KEY,"
                + MESSAGE+ " TEXT,"
                + STATUS + " TEXT)";
        db.execSQL(query);
    }

    public void addNewCourse(String To_ID,String Subject, String Message, String Status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TO_ID,To_ID);
        values.put(SUBJECT,Subject);
        values.put(MESSAGE,Message);
        values.put(STATUS,Status);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void update(String To_ID,String Subject, String Message, String Status) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TO_ID,To_ID);
        values.put(SUBJECT,Subject);
        values.put(MESSAGE,Message);
        values.put(STATUS,Status);

        db.update(TABLE_NAME, values, "Subject=?", new String[]{Subject});
        db.close();
    }

}
