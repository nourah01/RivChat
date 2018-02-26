package com.android.rivchat.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.rivchat.MainActivity;
import com.android.rivchat.R;
import com.android.rivchat.data.historyDB;

public class measurementres extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurementres);

        TextView TV = (TextView) findViewById(R.id.textView3);

        TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(measurementres.this, technique.class));
                measurementres.this.finish();}
        });
    }


}
