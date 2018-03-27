package com.android.rivchat.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.rivchat.R;
import com.android.rivchat.data.measurementDB;

public class gust_measurment_result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gust_measurment_result);
        TextView TV = (TextView) findViewById(R.id.textView3);
        TextView TVB = (TextView) findViewById(R.id.textView2);
        TextView TVR = (TextView) findViewById(R.id.textView4);
        final EditText EDN = (EditText) findViewById(R.id.editText2);
        String lastmesure=gustmesure.returndata();
        TVB.setText(lastmesure);
        float parseing_lastmesure = Float.parseFloat(lastmesure);
        if(parseing_lastmesure<60){
            TVR.setText("Not normal,\nlow heart beat rate");
        }
        else
        if(parseing_lastmesure>=60&&parseing_lastmesure <=99){
            TVR.setText("Normal heart beat rate,\nno stress");
        }
        else  if(parseing_lastmesure>=100&&parseing_lastmesure <=139){
            TVR.setText("High heart beat rate,\nstress");
        } else  if(parseing_lastmesure>=140){
            TVR.setText("Very High heart beat rate,\nneed to emergency call");
        }


        TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(gust_measurment_result.this, technique.class));
                gust_measurment_result.this.finish();}
        });
    }


}