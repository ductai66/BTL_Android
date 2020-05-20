package com.tai06.dothetai.danhba.DataBase;

public class DBQuery {
    public final static String DATABASE_NAME = " db_danhba ";
    public final static int DATABASE_VERSION = 1;
    public final static String TABLE_NAME = " dbo_danhba ";
    public final static String KEY_ID = "id ";
    public final static String KEY_NAME = "name ";
    public final static String KEY_NUMBER = "number ";
    public final static String SELECT_TABLE = " SELECT * FROM ";
    public final static String WHERE = "WHERE id = ";
    public final static String WHERE_LIKE = "WHERE name LIKE ";
    public final static String CREATE_TABLE = " CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NAME + " TEXT NOT NULL, " +
            KEY_NUMBER + " TEXT NOT NULL) ";

    public final static String TABLE_NAME_1 = " dbo_nhatky ";
    public final static String KEY_ID_1 = "id";
    public final static String KEY_NAME_1 = "name";
    public final static String KEY_NUMBER_1 = "number";
    public final static String DATE_TIME = "date";
    public final static String KEY_FOREIGN = "id_db";
    public final static String CREATE_TABLE_1 = " CREATE TABLE " + TABLE_NAME_1 + " (" + KEY_ID_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//            KEY_NAME_1 + " TEXT NOT NULL, " +
            KEY_NAME_1 + " TEXT , " +
            KEY_NUMBER_1 + " TEXT NOT NULL, " +
            DATE_TIME + " TEXT NOT NULL, " +
            KEY_FOREIGN + " INTEGER, "+
            " FOREIGN KEY(" + KEY_FOREIGN + ") REFERENCES " + DBQuery.TABLE_NAME_1+ "(id) )";
}
