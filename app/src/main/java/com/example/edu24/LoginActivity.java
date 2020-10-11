package com.example.edu24;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button mButton;
    TextView mRegisterTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mButton = findViewById(R.id.loginBtn);
        mRegisterTxt = findViewById(R.id.registerTxt);

        mButton.setOnClickListener(this);
        mRegisterTxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
//            case R.id.loginBtn:
//                Intent loginIntent = new Intent(this,RegisterActivity.class);
//                startActivity(loginIntent);
//                break;
            case R.id.registerTxt:
                Intent intent = new Intent(this,RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
