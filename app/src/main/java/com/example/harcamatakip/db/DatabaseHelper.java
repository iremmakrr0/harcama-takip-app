package com.example.harcamatakip.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "harcama.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
            "CREATE TABLE harcamalar (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "tutar REAL," +
            "kategori TEXT," +
            "aciklama TEXT," +
            "tarih INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS harcamalar");
        onCreate(db);
    }

    public void harcamaEkle(double tutar, String kategori, String aciklama, long tarih) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("tutar", tutar);
        cv.put("kategori", kategori);
        cv.put("aciklama", aciklama);
        cv.put("tarih", tarih);
        db.insert("harcamalar", null, cv);
    }

    public Cursor tumHarcamalar() {
        return getReadableDatabase()
            .rawQuery("SELECT * FROM harcamalar ORDER BY tarih DESC", null);
    }
}
