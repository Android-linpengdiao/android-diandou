package com.diandou.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.diandou.MyApplication;

public class DBHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "diandou.db";
    public final static String TABLE_NAME = "search_history";
    private final static int DB_VERSION = 3;
    private static volatile DBHelper sInstance;


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public synchronized static DBHelper getInstance() {
        if (sInstance == null) {
            synchronized (DBHelper.class) {
                if (sInstance == null) {
                    sInstance = new DBHelper(MyApplication.getInstance());
                }
            }
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createChatTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createChatTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE search_history (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userId VARCHAR," +
                "search_content VARCHAR)");
    }

}