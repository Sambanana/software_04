package com.example.mr_dankwah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    private TextView welcomeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        welcomeMessage = findViewById(R.id.welcomeUser);
        String username = getIntent().getStringExtra("name");
        welcomeMessage.setText("Welcome " + username);
    }

    public void logOut(View view) {
        Intent intent =  new Intent(this, SignIn.class);
        startActivity(intent);
    }
}