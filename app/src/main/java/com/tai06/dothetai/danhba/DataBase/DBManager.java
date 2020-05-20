package com.tai06.dothetai.danhba.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.tai06.dothetai.danhba.Activity.Activity_Info;
import com.tai06.dothetai.danhba.Fragment.Frag_ListPhone;
import com.tai06.dothetai.danhba.Object.History;
import com.tai06.dothetai.danhba.Object.Info;

import java.util.ArrayList;

public class DBManager extends SQLiteOpenHelper {
    Context context;

    public DBManager(@Nullable Context context) {
        super(context, DBQuery.DATABASE_NAME, null, DBQuery.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBQuery.CREATE_TABLE);
        db.execSQL(DBQuery.CREATE_TABLE_1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void AddInfo(Info info) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBQuery.KEY_NAME, info.getName());
        values.put(DBQuery.KEY_NUMBER, info.getNumber());
        db.insert(DBQuery.TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<Info> getInfo() {
        ArrayList<Info> lstInfo = new ArrayList<>();
        String query = DBQuery.SELECT_TABLE + DBQuery.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Info info = new Info();
                info.setId(cursor.getInt(0));
                info.setName(cursor.getString(1));
                info.setNumber(cursor.getString(2));
                lstInfo.add(info);
            } while (cursor.moveToNext());
        }
        db.close();
        return lstInfo;
    }

    public void AddHistory(History history) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBQuery.KEY_NAME_1,history.getName());
        values.put(DBQuery.KEY_NUMBER_1,history.getNumber());
        values.put(DBQuery.DATE_TIME,history.getDate());
        values.put(DBQuery.KEY_FOREIGN,history.getId_db());
        db.insert(DBQuery.TABLE_NAME_1,null,values);
        db.close();
    }
    public ArrayList<History> getHistory(){
        ArrayList<History> lsthistory = new ArrayList<>();
        String query = "SELECT * FROM " + DBQuery.TABLE_NAME_1 + " ORDER BY date DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do {
                History history = new History();
                history.setId(cursor.getInt(0));
                history.setName(cursor.getString(1));
                history.setNumber(cursor.getString(2));
                history.setDate(cursor.getString(3));
                history.setId_db(cursor.getInt(4));
                lsthistory.add(history);
            } while (cursor.moveToNext());
        }
        db.close();
        return lsthistory;
    }

    public void TestHistory(History history,String number){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBQuery.KEY_NAME_1,history.getName());
        values.put(DBQuery.KEY_NUMBER_1,history.getNumber());
        db.update(DBQuery.TABLE_NAME_1,values,DBQuery.KEY_NUMBER_1 + "=?", new String[]{number});
        db.close();
    }

    public void UpdateLoadHistory(History history){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = DBQuery.SELECT_TABLE + DBQuery.TABLE_NAME_1 + " WHERE " + DBQuery.KEY_ID_1 + " = '"+ history.getId() +"'";
        Cursor cursor = db.rawQuery(query,null);
        while(cursor.moveToNext()){
//            Activity_Info_History.name_info_history.setText(cursor.getString(1));
//            Activity_Info_History.number_info_history.setText(cursor.getString(2));
            Activity_Info.name_info.setText(cursor.getString(1));
            Activity_Info.number_info.setText(cursor.getString(2));
        }
        db.close();
    }

    public void UpdateInfo(Info info) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBQuery.KEY_NAME, info.getName());
        values.put(DBQuery.KEY_NUMBER, info.getNumber());
        db.update(DBQuery.TABLE_NAME, values, DBQuery.KEY_ID + "=?", new String[]{String.valueOf(info.getId())});
        db.close();
    }

    public void TestInfo(Info info,String number){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBQuery.KEY_NAME,info.getName());
        values.put(DBQuery.KEY_NUMBER,info.getNumber());
        db.update(DBQuery.TABLE_NAME,values,DBQuery.KEY_NUMBER + "=?", new String[]{number});
        db.close();
    }

    public void UpdateLoadInfo(Info info) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = DBQuery.SELECT_TABLE + DBQuery.TABLE_NAME + DBQuery.WHERE + "'" + info.getId() + "'";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            Activity_Info.name_info.setText(cursor.getString(1));
            Activity_Info.number_info.setText(cursor.getString(2));
        }
        db.close();
    }

    public void Delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBQuery.TABLE_NAME, DBQuery.KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public ArrayList<Info> getSearch() {
        ArrayList<Info> lstInfo = new ArrayList<>();
        String text_search = Frag_ListPhone.edit_search_view.getText().toString().trim();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = DBQuery.SELECT_TABLE + DBQuery.TABLE_NAME + DBQuery.WHERE_LIKE + "'%" + text_search + "%'";
        Cursor cursor = db.rawQuery(query, null);
        lstInfo.clear();
        if (cursor.moveToFirst()) {
            do {
                Info info = new Info();
                info.setId(cursor.getInt(0));
                info.setName(cursor.getString(1));
                info.setNumber(cursor.getString(2));
                lstInfo.add(info);
            } while (cursor.moveToNext());
        }
        db.close();
        return lstInfo;
    }

    //  CÁC HÀM DƯỚI ĐÂY KHÔNG DÙNG

    public void UpdateInfoTest(Info info,int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBQuery.KEY_NAME,info.getName());
        values.put(DBQuery.KEY_NUMBER,info.getNumber());
        db.update(DBQuery.TABLE_NAME,values,DBQuery.KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void UpdateHistory(History history,int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBQuery.KEY_NAME_1,history.getName());
        values.put(DBQuery.KEY_NUMBER_1,history.getNumber());
        db.update(DBQuery.TABLE_NAME_1,values,DBQuery.KEY_FOREIGN + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void UpdateHistoryTest(History history){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBQuery.KEY_NAME_1, history.getName());
        values.put(DBQuery.KEY_NUMBER_1, history.getNumber());
        db.update(DBQuery.TABLE_NAME_1, values, DBQuery.KEY_FOREIGN + "=?", new String[]{String.valueOf(history.getId_db())});
        db.close();
    }
}
