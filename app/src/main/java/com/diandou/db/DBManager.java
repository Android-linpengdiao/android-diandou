package com.diandou.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private static final String TAG = "DBManager";
    private static DBManager mInstance;

    public synchronized static DBManager getInstance() {
        if (null == mInstance) {
            mInstance = new DBManager();
        }
        return mInstance;
    }

    public void insertSearchContent(int userId, String content) {
        SQLiteDatabase db = DBHelper.getInstance().getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("userId", userId);
        values.put("search_content", content);
        db.insert(DBHelper.TABLE_NAME, null, values);
        db.close();
    }

    public List<String> querySearchHistory(int userId) {
        SQLiteDatabase db = DBHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_NAME, null, "userId = ?",
                new String[]{String.valueOf(userId)}, null, null, "_id DESC");
        List<String> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            list.add(cursor.getString(cursor.getColumnIndex("search_content")));
        }
        cursor.close();
        db.close();
        return list;
    }

    public void deleteSearchHistory(int userId) {
        SQLiteDatabase db = DBHelper.getInstance().getReadableDatabase();
        db.delete(DBHelper.TABLE_NAME, "userId = ?", new String[]{String.valueOf(userId)});
        db.close();
    }
}
