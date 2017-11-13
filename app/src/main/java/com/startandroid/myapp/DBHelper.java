package com.startandroid.myapp;



import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.net.URI;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "newDB";
    private static final int DATABASE_VERSION = 1;
    static final String ID_COLUMN = "id";
    static final String NAME_COLUMN = "name";
    static final String AGE_COLUMN = "age";
    static final String LASTNAME_COLUMN = "lastname";
    static final String IMG_COLUMN = "img";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table LastTable("
                + ID_COLUMN + " integer primary key autoincrement,"
                + NAME_COLUMN + " text,"
                + AGE_COLUMN + " text,"
                + LASTNAME_COLUMN + " text,"
                + IMG_COLUMN + " BLOB);"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS LastTable ");
        onCreate(db);
    }


    public boolean insertData(int id, String name, String age, String lastname, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if (id != 0) {
            contentValues.put(ID_COLUMN, id);
        }
        contentValues.put(NAME_COLUMN, name);
        contentValues.put(AGE_COLUMN, age);
        contentValues.put(LASTNAME_COLUMN, lastname);
        contentValues.put(IMG_COLUMN, image);
        long rowId = db.insert("LastTable", null, contentValues);
        if (rowId == -1)
            return false;
        else
            return true;
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM LastTable";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor showDataById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String idQuery = "SELECT * FROM LastTable WHERE id = ?";
        return db.rawQuery(idQuery, new String[]{String.valueOf(id)});
    }

    public boolean checkID(int id){
        boolean flag = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String idQuery = "SELECT * FROM LastTable WHERE id = ?";
        Cursor cur = db.rawQuery(idQuery, new String[]{String.valueOf(id)} );
        if(cur.getCount() > 0){
            flag = true;
        }
        return flag;
    }

    public boolean updateData(int id, String name, String age, String lastname, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_COLUMN, id);
        contentValues.put(NAME_COLUMN, name);
        contentValues.put(AGE_COLUMN, age);
        contentValues.put(LASTNAME_COLUMN, lastname);
        contentValues.put(IMG_COLUMN, image);
        int RowId = db.update("LastTable", contentValues, ID_COLUMN + "=" + id, null);
        if (RowId == -1)
            return false;
        else
            return true;
    }

    public void deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (id != 0) {
            db.delete("LastTable", ID_COLUMN + "=" + id, null);
        }
    }
    public Cursor getItemID(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + id + " FROM LastTable";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}

