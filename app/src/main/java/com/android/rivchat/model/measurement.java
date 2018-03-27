package com.android.rivchat.model;

/**
 * Created by shaimaaalkhamees on 15/03/2018 AD.
 */

public class measurement {
    public int id;
    public String rate;
    public String date_Time;
    public String note;
    public String Email;
    public measurement(String Email,String rate,String date_Time,String note){
        this.Email=Email;
        this.rate=rate;
        this.date_Time=date_Time;
        this.note=note;
    }

    public int getid(){return id;}
    public String getEmail(){return Email;}

    public String getRate(){return rate;}

    public String getdate_Time(){return date_Time;}

    public String getnote(){return note;}

}