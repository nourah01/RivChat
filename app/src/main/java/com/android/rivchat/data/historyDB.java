package com.android.rivchat.data;

;

/**
 * Created by shaimaaalkhamees on 11 فبر، 2018 م.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.android.rivchat.data.historyDB;
import com.android.rivchat.model.history;

import java.util.ArrayList;

public class historyDB extends SQLiteOpenHelper {

    public  static  final  String db_name="historydata.db";

    public historyDB(Context context) {
        super(context, db_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Measurements (id INTEGER PRIMARY KEY AUTOINCREMENT, HeartRate TEXT,Date TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Measurements");
        onCreate(db);
    }

    public boolean insertData(history history){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put("HeartRate",history.getRate());
        contentValues.put("Date", history.getdate());

        long result=db.insert("Measurements",null,contentValues);
        if (result==-1)
            return false;
        else return true;
    }


    public ArrayList getAllrecords(){
        ArrayList arrayList=new ArrayList();
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor res=db.rawQuery("select * from Measurements",null);
        res.moveToFirst();
        while (res.isAfterLast()==false){

            String s1=res.getString(0);
            String s2=res.getString(1);
            String s3=res.getString(2);

            arrayList.add(s1+"-"+s2+" :  "+s3);
            res.moveToNext();

        }
        return arrayList;

    }
    public ArrayList getAllid(){
        ArrayList arrayList=new ArrayList();
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor res=db.rawQuery("select * from Measurements",null);
        res.moveToFirst();
        while (res.isAfterLast()==false){

            String s1=res.getString(0);


            arrayList.add(s1);
            res.moveToNext();

        }
        return arrayList;

    }
    public Integer DeleteData(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete("Measurements","id = ?",new String[]{id});
    }

    public void DeleteAllData(){

        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("delete from "+ "Measurements");
        db.close();
    }



}
