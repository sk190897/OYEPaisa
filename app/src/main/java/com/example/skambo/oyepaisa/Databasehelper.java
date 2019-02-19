package com.example.skambo.oyepaisa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class Databasehelper extends SQLiteOpenHelper {


    public final static String DATABASE_NAME = "Myrecord.db";
    public final static String TABLE_NAME = "records_table";
    public final static String COL_1 = "DATE";
    public final static String COL_2 = "DESCRIPTION";
    public final static String COL_3 = "AMOUNT";

    public Databasehelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (DATE TEXT , DESCRIPTION TEXT , AMOUNT TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertdata(String date, String description, String amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1, date);
        contentValues.put(COL_2, description);
        contentValues.put(COL_3, amount);

        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;

    }

    public Cursor getdata(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE DATE = '" + date + "'";
        Cursor cursor = db.rawQuery(query, null);

        return cursor;

    }

    public Cursor getdata2(String d) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE DATE LIKE '%-" + d + "'";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;


    }

    public Integer deletedata(String date, String description, String amount) {

        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_NAME, "DATE=? AND DESCRIPTION=? AND AMOUNT=?", new String[]{date, description, amount});


    }


}
