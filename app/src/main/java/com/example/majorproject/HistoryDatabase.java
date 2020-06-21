package com.example.majorproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class HistoryDatabase extends SQLiteOpenHelper {

    public HistoryDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("DROP TABLE IF EXISTS " + DBstruct.TBL_NAME);
        db.execSQL(DBstruct.SQL_CREATE_TBL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void insert_values(String date, String device, String filename, int kind, int isSuccess){
        SQLiteDatabase db = getWritableDatabase();
        String sqlInsert;
        sqlInsert = DBstruct.SQL_INSERT + "(" +
                "'" + date + "', " +
                "'" + device + "', " +
                "'" + filename + "', " +
                kind + ", " +
                isSuccess + ")";
        db.execSQL(sqlInsert);

        db.close();

    }
}
