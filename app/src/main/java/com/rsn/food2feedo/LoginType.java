package com.rsn.food2feedo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginType extends AppCompatActivity {
Button b1,b2;
TextView abt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_type);
        b1=findViewById(R.id.donor);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginType.this,DonorLogin.class);
                startActivity(i);
            }
        });
        b2=findViewById(R.id.admin);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginType.this,AdminLogin.class);
                startActivity(i);
            }
        });
        abt=findViewById(R.id.about_us);
        abt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginType.this,AboutUs.class);
                startActivity(i);
            }
        });
    }
}