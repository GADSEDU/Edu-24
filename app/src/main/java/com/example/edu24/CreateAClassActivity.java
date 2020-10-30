package com.example.edu24;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.edu24.model.Classes;
import com.example.edu24.model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.SecureRandom;
import java.util.ArrayList;

public class CreateAClassActivity extends AppCompatActivity {
    private static final String TAG = "Create Class";
    private Toolbar toolbar;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private DatabaseReference classRef;
    private Button create;
    private TextInputEditText className,section,Room,Subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_a_class);
        toolbar = findViewById(R.id.create_toolbar);
        setSupportActionBar(toolbar);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference().child(getString(R.string.db_users));
        classRef = database.getReference().child(getString(R.string.db_classes));
        create = findViewById(R.id.create_btn);
        className = findViewById(R.id.class_name);
        section = findViewById(R.id.section);
        Room = findViewById(R.id.room);
        Subject = findViewById(R.id.subject);

        create.setOnClickListener(view -> {
            String class_name = className.getText().toString();
            String sectiontxt = section.getText().toString();
            String roomtxt = Room.getText().toString();
            String subjecttxt = Subject.getText().toString();
            if (!class_name.isEmpty() && !subjecttxt.isEmpty()){
                SaveToDatabase(class_name,sectiontxt,roomtxt,subjecttxt);
            }
        });
    }

    private void SaveToDatabase(String class_name, String sectiontxt, String roomtxt, String subjecttxt) {
        String userID = auth.getCurrentUser().getUid();
        String class_code = autoCode();
        Classes aClass = new Classes(userID,class_name,class_code,sectiontxt,roomtxt,subjecttxt);
        classRef.push().setValue(aClass);

        saveToUserClass();
    }

    private void saveToUserClass() {
        Query query = classRef.orderByChild("class_teacher")
                .equalTo(auth.getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot singleDataSnapshot : snapshot.getChildren()) {
//                    String userID = auth.getCurrentUser().getUid();
//                    Classes classes = singleDataSnapshot.getValue(Classes.class);
//                    User user = new User();
//                    ArrayList<String> userclass = new ArrayList<>();
//                    userclass.add(snapshot.getKey());
//                    Log.d(TAG, "onDataChange: " +snapshot.getChildren());
////                    Log.d(TAG, "onDataChange: " +userclass);
////                    user.setUser_classes(userclass);
////                    userRef.child(userID).setValue(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private String autoCode() {
        int length = 8;
        String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        String CHAR_UPPER = CHAR_LOWER.toUpperCase();
        String NUMBER = "0123456789";

        String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
        SecureRandom random = new SecureRandom();

        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            // 0-62 (exclusive), random returns 0-61
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

            sb.append(rndChar);
        }

        return sb.toString();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}