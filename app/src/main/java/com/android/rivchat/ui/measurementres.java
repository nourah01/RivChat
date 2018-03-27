package com.android.rivchat.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.android.rivchat.MainActivity;
import com.android.rivchat.R;
import com.android.rivchat.data.measurementDB;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class measurementres extends AppCompatActivity {
    private Context mContext;
    private Activity mActivity;
    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 1;
    private LinearLayout mRootLayout;
    private Button  emr_btn;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurementres);
         emr_btn=(Button)findViewById(R.id.button4);
        TextView TV = (TextView) findViewById(R.id.textView3);
        TextView TVB = (TextView) findViewById(R.id.textView2);
        TextView TVR = (TextView) findViewById(R.id.textView4);
        final EditText EDN = (EditText) findViewById(R.id.editText2);
        emr_btn.setVisibility(View.GONE);
        final measurementDB db=new measurementDB(this);
        String lastmesure=AddGroupActivity.returndata();
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
            TV.setVisibility(View.GONE);
            emr_btn.setVisibility(View.VISIBLE);

        }

        final String lastid=db.getLastId();

       TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.UpDateNote(EDN.getText().toString(),lastid.toString());
                startActivity(new Intent(measurementres.this, technique.class));
                measurementres.this.finish();}
        });

        emr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = "0501470728";

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
}