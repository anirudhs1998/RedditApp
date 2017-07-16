package com.example.anirudhs.redditapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Anirudh S on 16-07-2017.
 */

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "reddit.db";
    public static final String TABLE_REDDIT = "reddit";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DESCR = "descr";
    public static final String COLUMN_COMM = "comm";
    public static final String IMG_URL = "imgurl";




    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }




    public  void addReddit(Listitem listitem){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DESCR,listitem.getDescr());
        contentValues.put(COLUMN_COMM,listitem.getComm());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_REDDIT,null,contentValues);
        db.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE" + TABLE_REDDIT + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " +
                COLUMN_DESCR + " TEXT " +
                COLUMN_COMM + " INTEGER " +
                IMG_URL + " TEXT " +
                ");";
            db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REDDIT);
        onCreate(db);

    }




    public String dataBasetoString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_REDDIT + "WHERE 1";

        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        while(!c.isAfterLast()){
                if(c.getString(c.getColumnIndex("descr")) != null){
                    dbString += c.getString(c.getColumnIndex("descr"));
                    dbString += "\n";

                }

        }
        db.close();
        return dbString;

    }
}
