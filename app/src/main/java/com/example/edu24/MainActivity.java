package com.example.edu24;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialiseFirebase();
        setContentView(R.layout.activity_main);
    }

    private void initialiseFirebase() {
        FirebaseUtil.openFirebaseReference("users", this);
        FirebaseUtil.attachListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUtil.detachListener();
    }
}
