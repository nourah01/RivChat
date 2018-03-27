package com.android.rivchat.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.rivchat.model.measurement;

import java.util.ArrayList;

/**
 * Created by shaimaaalkhamees on 15/03/2018 AD.
 */

public class measurementDB extends SQLiteOpenHelper {

    public  static  final  String db_name="measurementdata.db";

    public measurementDB(Context context) {
        super(context, db_name, null, 9);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Measurements (id INTEGER PRIMARY KEY AUTOINCREMENT,Email TEXT,Date_Time TEXT,HeartRate TEXT,Note TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Measurements");
        onCreate(db);
    }

    public boolean insertData(measurement measurement){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Email", measurement.getEmail());
        contentValues.put("Date_Time", measurement.getdate_Time());
        contentValues.put("HeartRate",measurement.getRate());
        contentValues.put("Note", measurement.getnote());

        long result=db.insert("Measurements",null,contentValues);
        if (result==-1)
            return false;
        else return true;
    }


    public ArrayList getAllrecords(String email){
        ArrayList arrayList=new ArrayList();
        SQLiteDatabase db=this.getReadableDatabase();
        //String q = "SELECT * FROM Measurements WHERE Email = " + email  ;
        Cursor res=db.rawQuery("SELECT * FROM  Measurements  WHERE  Email ='"+email+"'", null);

        res.moveToFirst();
        while (res.isAfterLast()==false){

            String s1=res.getString(0);
            String s2=res.getString(2);
            String s3=res.getString(3);
            String s4=res.getString(4);
            arrayList.add("ID:"+s1+"\n   "+s2+"\n   Heart bate rate: "+s3+"\n   NOTE:"+s4);
            res.moveToNext();

        }
        return arrayList;

    }
    public String getLastMeasurements(){
        ArrayList arrayList=new ArrayList();
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor res=db.rawQuery("select * from Measurements",null);
        res.moveToFirst();
        while (res.isAfterLast()==false){

            String s1=res.getString(2);


            arrayList.add(s1);
            res.moveToNext();

        }
        return arrayList.get(arrayList.size()-1)+"";

    }
    public String getLastId(){
        ArrayList arrayList=new ArrayList();
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor res=db.rawQuery("select * from Measurements",null);
        res.moveToFirst();
        while (res.isAfterLast()==false){

            String s1=res.getString(0);


            arrayList.add(s1);
            res.moveToNext();

        }
        return arrayList.get(arrayList.size()-1)+"";

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

    public void UpDateNote(String note,String id){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Note", note);
        db.update("Measurements", contentValues, "id= ?", new String[]{id});
    }

}
