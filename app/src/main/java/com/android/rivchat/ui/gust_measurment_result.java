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
    public String sharing_result="Hello,\nThe result of measurement for today is:\n ";
    public TextView TVR,TVB;
    public boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gust_measurment_result);
        TextView TV = (TextView) findViewById(R.id.textView3);
        TVB = (TextView) findViewById(R.id.textView2);
        TVR = (TextView) findViewById(R.id.textView4);
        final EditText EDN = (EditText) findViewById(R.id.editText2);
        String lastmesure = gustmesure.returndata();
        TVB.setText(lastmesure);
        float parseing_lastmesure = Float.parseFloat(lastmesure);
        if (parseing_lastmesure < 60) {
            flag = true;
            TV.setVisibility(View.GONE);
            TVR.setText("you are stressed a bit,\n try to do some techniques to feel better ! ");
        } else if (parseing_lastmesure >= 60 && parseing_lastmesure <= 99) {
            flag = true;
            TV.setVisibility(View.GONE);
            TVR.setText("you seem to be in a good mood,\n keep it up !");

        } else if (parseing_lastmesure >= 100 && parseing_lastmesure <= 139) {
            TVR.setText("you are stressed a bit,\n try to do some techniques to feel better ! ");

        } else if (parseing_lastmesure >= 140) {
            TVR.setText("your heartbeat are so fast,\n want to make an emergency call? ");
            TV.setVisibility(View.GONE);
        }

        if (flag == true){}
    else{
            TV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(gust_measurment_result.this, technique.class));
                    gust_measurment_result.this.finish();
                }
            });
        }
    }

    public void share(View view) {
        Intent myint=new Intent(Intent.ACTION_SEND);
        myint.setType("text/plain");
        sharing_result=sharing_result.concat(TVR.getText().toString());
        sharing_result=sharing_result.concat("\n heart beat rate:");
        sharing_result=sharing_result.concat(TVB.getText().toString());
        String shareBody=sharing_result.concat("\n\n This measurement was obtained by stress management application\nthank you for using SM :)");
        String sharesub="Measurement result";
        myint.putExtra(Intent.EXTRA_SUBJECT,sharesub);
        myint.putExtra(Intent.EXTRA_TEXT,shareBody);
        startActivity(Intent.createChooser(myint,"Share using"));
    }
}