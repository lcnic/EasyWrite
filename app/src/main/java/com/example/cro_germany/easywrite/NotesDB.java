package com.example.cro_germany.easywrite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotesDB extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "notes";
    public static final String CONTENT = "content";
    public static final String PATH = "path";
    public static final String VIDEO = "video";
    public static final String ID = "_id";
    public static final String TIME = "time";

    //重写构造方法，super返回值（context,库名,factory,版本号）
    public NotesDB(Context context) {
        super(context, "notes", null, 1);
    }

    //重写onCreate方法创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + CONTENT
                + " TEXT NOT NULL," + PATH + " TEXT NOT NULL," + VIDEO
                + " TEXT NOT NULL," + TIME + " TEXT NOT NULL)");
    }

    //重写onUpgrade方法更新数据库
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
