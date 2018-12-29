package com.example.nikhilreddy.libraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

public class splashScreenActivity extends AppCompatActivity {
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);
        final Thread myThread=new Thread(){
            @Override
            public void run(){
                try{
                    sleep(1500);
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

        };
        myThread.start();
    }
}