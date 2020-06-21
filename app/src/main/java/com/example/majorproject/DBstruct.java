package com.example.majorproject;

public class DBstruct {
    private DBstruct() {};

    public static final String TBL_NAME = "historyTBL";
    public static final String COL_NO = "NO";
    public static final String COL_DATE = "DATE";
    public static final String COL_DEVICE = "DEVICE";
    public static final String COL_FILENAME = "FILENAME";
    public static final String COL_KIND = "KIND";
    public static final String COL_SUCCESS = "ISSUCCESS";

    public static final String SQL_CREATE_TBL
            = "CREATE TABLE IF NOT EXISTS " + TBL_NAME + " " +
            "(" +
            COL_NO + " INTEGER PRIMARY KEY AUTOINCREMENT" + ", " +
            COL_DATE + " TEXT" + ", " +
            COL_DEVICE + " TEXT" + ", " +
            COL_FILENAME + " TEXT" + ", " +
            COL_KIND + " INTEGER" + ", " +
            COL_SUCCESS + " INTEGER" +
            ");";
    public static final String SQL_SELECT
            = "SELECT * FROM " + TBL_NAME;

    public static final String SQL_DELETE
            = "DELETE FROM " + TBL_NAME;

    public static final String SQL_INSERT
            = "INSERT OR REPLACE INTO " + TBL_NAME + " " +
            "(" + COL_DATE + ", " + COL_DEVICE + ", " +
            COL_FILENAME + ", " + COL_KIND + ", " + COL_SUCCESS + ") VALUES ";

}
