package com.example.geoqiuz;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper  extends SQLiteOpenHelper{

    public static String DB_PATH;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MonarchDB.db";
    public static final String MONARCH_TABLE = "MonarchTABLE";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_DATE_START = "dataStart";
    public static final String KEY_DATE_FINISH = "dataFinish";
    private Context myContext;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext=context;
        DB_PATH = context.getFilesDir() + DATABASE_NAME;
        Log.d("mLog",DB_PATH);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + MONARCH_TABLE + "(" + KEY_ID
                + " integer primary key," + KEY_NAME + " text," + KEY_DATE_START + " text" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + MONARCH_TABLE);
        onCreate(db);
    }
    SQLiteDatabase open()throws SQLException {

        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
}