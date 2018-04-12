package com.android.rivchat.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.rivchat.R;

public class adminlogin extends AppCompatActivity {
    public String adminusername;
    public String adminpassword;
    public    EditText username;
    public    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        username =(EditText)findViewById(R.id.et_username);
        password =(EditText)findViewById(R.id.et_password);
    }

    public void clickLogin(View view) {
        adminusername =username.getText().toString();
        adminpassword=password.getText().toString();
        if(adminusername.equals("admin@stressmanagment.com")&& adminpassword.equals("stress_managment_team_123456")){
            Intent intent= new Intent(this,admin.class);
            startActivity(intent);
        }
        else     Toast.makeText(this, "invalid username or password", Toast.LENGTH_SHORT).show();
    }
}
