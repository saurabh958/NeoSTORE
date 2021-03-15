package com.example.saurabhneostore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.saurabhneostore.Login.Login;
import com.example.saurabhneostore.homepage.Homepage;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE);
        //boolean hasloggedin = sp.getBoolean("hasloggedin", false);
        String fname= sp.getString("FName","");


        if(fname.equals(""))
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d("saurabh-","if");
                    Intent intent = new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
                    Log.d("saurabh-","if");
                    finish();
                }
            }, SPLASH_SCREEN);
        }
        else
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d("saurabh-","else");
                    Intent intent = new Intent(MainActivity.this, Homepage.class);
                    startActivity(intent);
                    Log.d("saurabh-","else");
                    finish();
                }
            }, SPLASH_SCREEN);
        }

    }
}