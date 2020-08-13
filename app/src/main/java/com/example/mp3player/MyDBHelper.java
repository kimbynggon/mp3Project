package com.example.mp3player;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;


public class MyDBHelper extends SQLiteOpenHelper {
    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    public MyDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table mp3TBL(name char(20) primary key, title char , album char)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("drop table if exists mp3TBL");
        onCreate(sqLiteDatabase);
    }

//    public ArrayList<MusicData> selectmp3TBL() {
//        ArrayList<MusicData> musicDatalist = new ArrayList<MusicData>();
//        sqLiteDatabase = this.getReadableDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery("select * from mp3TBL;", null);
//
//        String strnames = "가수이름" + "\r\n" + "--------" + "\r\n";
//        String strtitle = "제목" + "\r\n" + "--------" + "\r\n";
//
//        while (cursor.moveToNext()){
//           MusicData musicData = new MusicData(cursor.getString(0),cursor.getString(1),cursor.getInt(3));
//        }
//    }

}
