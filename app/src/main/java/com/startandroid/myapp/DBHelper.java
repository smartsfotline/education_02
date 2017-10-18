package com.startandroid.myapp;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "myDB";
    private static final int DATABASE_VERSION = 1;
    static final String ID_COLUMN = "id";
    static final String NAME_COLUMN = "name";
    static final String AGE_COLUMN = "age";
    static final String LASTNAME_COLUMN = "lastname";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table FirstTable("
                + ID_COLUMN + " integer primary key autoincrement,"
                + NAME_COLUMN + " text,"
                + AGE_COLUMN + " text,"
                + LASTNAME_COLUMN + " text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS FirstTable ");
        onCreate(db);
    }

    public boolean insertData(int id, String name, String age, String lastname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if (id != 0) {
            contentValues.put(ID_COLUMN, id);
        }
        contentValues.put(NAME_COLUMN, name);
        contentValues.put(AGE_COLUMN, age);
        contentValues.put(LASTNAME_COLUMN, lastname);
        long rowId = db.insert("FirstTable", null, contentValues);
        if (rowId == -1)
            return false;
        else
            return true;
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM FirstTable";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor showDataById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String idQuery = "SELECT * FROM FirstTable WHERE id = ?";
        return db.rawQuery(idQuery, new String[]{String.valueOf(id)});
    }

    public boolean checkID(int id){
        boolean flag = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String idQuery = "SELECT * FROM FirstTable WHERE id = ?";
        Cursor cur = db.rawQuery(idQuery, new String[]{String.valueOf(id)} );
        if(cur.getCount() > 0){
            flag = true;
        }
        return flag;
    }

    public boolean updateData(int id, String name, String age, String lastname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_COLUMN, id);
        contentValues.put(NAME_COLUMN, name);
        contentValues.put(AGE_COLUMN, age);
        contentValues.put(LASTNAME_COLUMN, lastname);
        int RowId = db.update("FirstTable", contentValues, ID_COLUMN + "=" + id, null);
        if (RowId == -1)
            return false;
        else
            return true;
    }

    public void deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (id != 0) {
            db.delete("FirstTable", ID_COLUMN + "=" + id, null);
        }
    }
    public Cursor getItemID(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + id + " FROM FirstTable";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}

