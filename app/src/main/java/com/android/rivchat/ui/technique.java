package com.android.rivchat.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.rivchat.R;
import com.android.rivchat.model.imageUpload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class technique extends AppCompatActivity {
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private List<imageUpload> imgList;
    private ListView lv;
    private ProgressDialog progressDialog;
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technique);
        iv=(ImageView)findViewById(R.id.imgView);
        // final TextView tvName = (TextView) findViewById(R.id.tvImageName);

        imgList = new ArrayList<>();
        imgList.clear();
        //Show progress dialog during list image loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Waiting for technique ...");
        progressDialog.show();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference(admin.FB_DATABASE_PATH);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                //Fetch image data from firebase database
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //ImageUpload class require default constructor
                    imageUpload img = snapshot.getValue(imageUpload.class);
                    imgList.add(img);
                }
                int size=imgList.size();
                Random r = new Random();
                int Low = 0;
                int High = size-1;
               // Toast.makeText(technique.this, size+"", Toast.LENGTH_SHORT).show();

                int Result;
                if(size>1)
                Result =r.nextInt(High-Low) + Low;
                else Result=0;
                // Load the image using Picasso
                //tvName.setText(imgList.get(Result).getName());
                Picasso.with(getApplicationContext()).load(imgList.get(Result).getUrl()).into(iv);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();
            }
        });

    }


    public void get(View view) {
        startActivity(new Intent(technique.this, technique.class));
        technique.this.finish();
    }
}

