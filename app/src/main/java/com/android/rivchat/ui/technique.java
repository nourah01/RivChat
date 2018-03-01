package com.android.rivchat.ui;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.android.rivchat.Manifest;
import com.android.rivchat.R;

public class technique extends AppCompatActivity {
    final  int req=999;
Button btn4;
ImageView myimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technique);
        btn4=(Button)findViewById(R.id.button4);
        myimg=(ImageView)findViewById(R.id.imageView5);
     /*   btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(technique.this,
           //             new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    //    100
                        );
                         }
     }); */
    }
}
