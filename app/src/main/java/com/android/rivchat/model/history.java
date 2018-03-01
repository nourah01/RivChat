
package com.android.rivchat.model;

import java.util.Date;

/**
 * Created by shaimaaalkhamees on 11 فبر، 2018 م.
 */

public class history {
    public int id;
    public String rate;
    public String date;
    public String note;
    public history(String rate,String date,String note){
        this.rate=rate;
        this.date=date;
        this.note=note;
    }

    public int getid(){return id;}

    public String getRate(){return rate;}

    public String getdate(){return date;}

    public String getnote(){return note;}
}
