package com.example.edu24;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialiseFirebase();
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.registerText);
        mTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.registerText:
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
        }
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
