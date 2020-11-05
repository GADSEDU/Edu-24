package com.example.edu24.ui.people;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.edu24.R;
import com.example.edu24.adapter.ClassAdapter;
import com.example.edu24.adapter.StudentAdapter;
import com.example.edu24.model.Classes;
import com.example.edu24.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class PeopleFragment extends Fragment {
    private static final String TAG = "people";
    private TextView teacherName;
    private CircleImageView teacherImage;
    private RecyclerView recyclerView;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private DatabaseReference classRef;
    private StudentAdapter adapter;
    private List<User> studentList = new ArrayList<>();
    private String classId;

    public PeopleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_people, container, false);
        teacherName = root.findViewById(R.id.teacher_name);
        teacherImage = root.findViewById(R.id.teacher_profile_image);
        recyclerView = root.findViewById(R.id.students_recycler_view);
        //        initialize Firebase
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference().child(getString(R.string.db_users));
        classRef = database.getReference().child(getString(R.string.db_classes));
        classId = getActivity().getIntent().getStringExtra("Id");
        Log.d(TAG, "onCreateView: " + classId);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new StudentAdapter(studentList,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        getUsers(classId);
    }

    private void getUsers(String classId) {
        Query query = classRef.child(classId)
                .orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Classes classes = snapshot.getValue(Classes.class);
                Log.d(TAG, "onDataChange: " + classes.getClass_student());
                getTeachers(classes.getClass_teacher());
                getStudent(classes.getClass_student());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getStudent(HashMap<String, String> class_student) {
        for (Map.Entry m: class_student.entrySet()){
            Query query = userRef.child(m.getValue().toString())
                    .orderByKey();
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    User students = new User();
                    students.setUser_first_name(user.getUser_first_name());
                    students.setUser_surname(user.getUser_surname());
                    students.setProfile_image(user.getProfile_image());
                    studentList.add(students);
                    adapter.notifyItemInserted(studentList.size()-1);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void getTeachers(String class_teacher) {
        Query query = userRef.child(class_teacher)
                .orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                teacherName.setText(user.getUser_first_name() + " "+ user.getUser_surname());
                if (!user.getProfile_image().equals(null)){
                    Picasso.get().load(Uri.parse(user.getProfile_image()))
                            .into(teacherImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}