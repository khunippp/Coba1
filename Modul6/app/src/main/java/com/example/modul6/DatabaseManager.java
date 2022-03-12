package com.example.modul6;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseManager {

    private static final String ROW_ID = "_id";
    private static final String ROW_NAMA = "nama";
    private static final String ROW_HOBI = "hobi";

    private static final String NAMA_DB = "Databasehobi"; //nama database
    private static final String NAMA_TABEL = "hobiku"; //nama label
    private static final int DB_VERSION = 1;

    //CREATE TABLE hobiku (_id integer PRIMARY KEY autoincrement, nama text, hobi text)

    private static final String CREATE_TABLE = "create table "+NAMA_TABEL+"("+ROW_ID+" integer PRIMARY KEY autoincrement, "+ROW_NAMA+" text,"+ROW_HOBI+" text)";
    private final Context context;
    private DatabaseOpenHelper dbHelper;
    private SQLiteDatabase db;

    public DatabaseManager(Context ctx){
        this.context=ctx;
        dbHelper=new DatabaseOpenHelper(context);
        db=dbHelper.getWritableDatabase();
    }

    public static class DatabaseOpenHelper extends SQLiteOpenHelper{

        public DatabaseOpenHelper(Context context){
            super(context, NAMA_DB, null, DB_VERSION);
            //TODO Auto-generated constructor stub
        }

        @Override public void onCreate(SQLiteDatabase db){
            //TODO Auto-generated method stub
            db.execSQL(CREATE_TABLE);
        }

        @Override public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
            //TODO Auto-generated method stub"DROP TABLE IF EXISTS titles"
            db.execSQL("DROP TABLE IF EXISTS "+NAMA_DB);
            onCreate(db);
        }
    }

    public void close() {
        dbHelper.close();
    }

    public void TambahRow(String nama, String hobi){
        ContentValues values = new ContentValues();
        values.put(ROW_NAMA, nama);
        values.put(ROW_HOBI, hobi);
        try{

            //db.delete(NAMA_TABEL, null, null);
            db.insert(NAMA_TABEL, null, values);
        }
        catch (Exception e){
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<Object>>ambilSemuaBaris(){
        ArrayList<ArrayList<Object>> dataArray = new ArrayList<>();
        Cursor cur;
        try{
            cur = db.query(NAMA_TABEL, new String[]{ROW_ID,ROW_NAMA,ROW_HOBI}, null, null, null, null, null);
            cur.moveToFirst();
            if(!cur.isAfterLast()){
                do {
                    ArrayList<Object> dataList = new ArrayList<>();
                    dataList.add(cur.getLong(0));
                    dataList.add(cur.getString(1));
                    dataList.add(cur.getString(2));

                    dataArray.add(dataList);
                }
                while (cur.moveToNext());
            }
        }
        catch (Exception e){
            //TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("DEBE ERROR", e.toString());
        }
        return dataArray;
    }
}