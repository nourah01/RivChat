package com.android.rivchat.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.FragmentTransaction;
import android.app.Fragment;
import com.android.rivchat.MainActivity;
import com.android.rivchat.R;
import com.android.rivchat.ui.MeasureFragment;
import com.android.rivchat.data.measurementDB;


public class measurementres extends AppCompatActivity {
    private Context mContext;
    private Activity mActivity;
    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 1;
    private LinearLayout mRootLayout;
    private Button  emr_btn;
    public String sharing_result="Hello,\nThe result of measurement for today is:\n ";
    public TextView TVR,TVB;
    public EditText EDN;
    public measurementDB db;
    public String lastid;
    public boolean flag=false;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurementres);
         emr_btn=(Button)findViewById(R.id.button4);
        TextView TV = (TextView) findViewById(R.id.textView3);
        TVB = (TextView) findViewById(R.id.textView2);
        TVR = (TextView) findViewById(R.id.textView4);
      EDN = (EditText) findViewById(R.id.editText2);
        emr_btn.setVisibility(View.GONE);
        db=new measurementDB(this);
        String lastmesure=AddGroupActivity.returndata();
        TVB.setText(lastmesure);
        float parseing_lastmesure = Float.parseFloat(lastmesure);
       if(parseing_lastmesure<60){
           flag=true;
           TV.setVisibility(View.GONE);
           TVR.setText("you are stressed a bit,\n try to do some techniques to feel better ! ");
        }
        else
        if(parseing_lastmesure>=60&&parseing_lastmesure <=99){
            flag=true;
            TV.setVisibility(View.GONE);
            TVR.setText("you seem to be in a good mood,\n keep it up !");

        }
        else  if(parseing_lastmesure>=100&&parseing_lastmesure <=139){
            TVR.setText("you are stressed a bit,\n try to do some techniques to feel better ! ");

        } else  if(parseing_lastmesure>=140){
            TVR.setText("your heartbeat are so fast,\n want to make an emergency call? ");
            TV.setVisibility(View.GONE);
            emr_btn.setVisibility(View.VISIBLE);

        }

     lastid=db.getLastId();
        if(flag==true){
            //flag=false;
        }
        else {

            TV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    db.UpDateNote(EDN.getText().toString() + "\n" + TVR.getText().toString(), lastid.toString());
                    startActivity(new Intent(measurementres.this, technique.class));
                    measurementres.this.finish();
                }
            });
        }
        emr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = "911";
                db.UpDateNote(EDN.getText().toString()+"\n"+TVR.getText().toString(),lastid.toString());

                if (!TextUtils.isEmpty(phoneNumber)) {
                    if (checkPermission(Manifest.permission.CALL_PHONE)) {
                        String dial = "tel:" + phoneNumber;
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                    } else {
                        Toast.makeText(measurementres.this, "Permission Call Phone denied", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(measurementres.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (checkPermission(Manifest.permission.CALL_PHONE)) {
            emr_btn.setEnabled(true);
        } else {
            emr_btn.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, MAKE_CALL_PERMISSION_REQUEST_CODE);
        }

    }

    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case MAKE_CALL_PERMISSION_REQUEST_CODE :
                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    emr_btn.setEnabled(true);
                    Toast.makeText(this, "You can call the number by clicking on the button", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        db.UpDateNote(EDN.getText().toString()+"\n"+TVR.getText().toString(),lastid.toString());
    }

    @Override
    public void onStop() {
        super.onStop();
        db.UpDateNote(EDN.getText().toString()+"\n"+TVR.getText().toString(),lastid.toString());
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