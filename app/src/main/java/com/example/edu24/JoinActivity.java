package com.example.edu24;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.edu24.model.Classes;
import com.example.edu24.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JoinActivity extends AppCompatActivity {
    private static final String TAG = "Join Class";
    private Toolbar toolbar;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference classRef;
    private DatabaseReference userRef;
    private Button join;
    private TextInputEditText code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        toolbar = findViewById(R.id.join_toolbar);
        setSupportActionBar(toolbar);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        classRef = database.getReference().child(getString(R.string.db_classes));
        userRef = database.getReference().child(getString(R.string.db_users));
        join = findViewById(R.id.join_class_button);
        code = findViewById(R.id.join_code);

        join.setOnClickListener(view -> {
            if (NetworkUtils.isNetworkConnected(this)){
                String codetxt = code.getText().toString();
                getClasses(codetxt);
            }else {
                Snackbar.make(view, "Internet connection failed", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void getClasses(String codetxt) {
        Query query = classRef.orderByChild("class_code")
                .equalTo(codetxt);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String userID = auth.getCurrentUser().getUid();
                String classId = snapshot.getKey();
                classRef.child(classId).child("class_student").child(userID).setValue(userID);
                userRef.child(userID).child("user_classes").child(classId).setValue(classId).addOnSuccessListener(aVoid -> finish());

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(JoinActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}