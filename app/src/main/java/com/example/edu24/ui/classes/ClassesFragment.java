package com.example.edu24.ui.classes;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.edu24.CreateAClassActivity;
import com.example.edu24.JoinActivity;
import com.example.edu24.NetworkUtils;
import com.example.edu24.adapter.ClassAdapter;
import com.example.edu24.model.Classes;
import com.example.edu24.model.ModelClass;
import com.example.edu24.R;
import com.example.edu24.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClassesFragment extends Fragment {
    private static final String TAG = "class";
    private RecyclerView mRecyclerView;
    private List<Classes> modelClassList = new ArrayList<>();
    private FloatingActionButton fab;
    private AlertDialog.Builder dialogBuilder;
    private Button create,join;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private DatabaseReference classRef;
    private ClassAdapter adapter;
    private SwipeRefreshLayout swipe;

    public ClassesFragment () {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_classes, container, false);
//        initialize Firebase
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference().child(getString(R.string.db_users));
        classRef = database.getReference().child(getString(R.string.db_classes));

        fab = root.findViewById(R.id.class_fab);
        swipe = root.findViewById(R.id.main_refresh);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ClassAdapter(modelClassList,getContext());
        mRecyclerView = view.findViewById(R.id.class_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if (NetworkUtils.isNetworkConnected(getContext())){
            listOfClass();
        }else{
            Snackbar.make(view, "Internet connection failed", Snackbar.LENGTH_LONG).show();
        }
        swipe.setOnRefreshListener(() -> {
            new Handler().postDelayed(() -> {
                if (NetworkUtils.isNetworkConnected(getContext())){
//                    listOfClass();
                    swipe.setRefreshing(false);
                }else {
                    Snackbar.make(view, "Internet connection failed", Snackbar.LENGTH_LONG).show();
                    swipe.setRefreshing(false);
                }
            },2000);
        });
        fab.setOnClickListener(views -> {
            dialogBuilder = new AlertDialog.Builder(getContext(),R.style.Theme_MaterialComponents_Light_Dialog_Alert);
            View layoutView = getLayoutInflater().inflate(R.layout.user_choice, null);
            create = layoutView.findViewById(R.id.create_button);
            join = layoutView.findViewById(R.id.join_button);
            dialogBuilder.setView(layoutView);
            AlertDialog alertDialog = dialogBuilder.create();
            create.setOnClickListener(view1 -> {
                startActivity(new Intent(getContext(), CreateAClassActivity.class));
                alertDialog.cancel();
            });
            join.setOnClickListener(view12 -> {
                startActivity(new Intent(getContext(), JoinActivity.class));
                alertDialog.cancel();
            });
            alertDialog.show();
    });
    }

    private void listOfClass() {
        String userId = auth.getCurrentUser().getUid();
        Query query = userRef.child(userId).child("user_classes")
                .orderByKey();
                query.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Query query = classRef.child(snapshot.getKey())
                                .orderByKey();
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Classes classes = snapshot.getValue(Classes.class);
                                    Classes clam = new Classes();
                                    clam.setClass_name(classes.getClass_name());
                                    clam.setClass_student(classes.getClass_student());
                                    clam.setClass_section(classes.getClass_section());
                                    clam.setClass_code(classes.getClass_code());
                                    clam.setClass_teacher(classes.getClass_teacher());
                                    clam.setClass_id(snapshot.getKey());
                                    modelClassList.add(clam);
                                    adapter.notifyItemInserted(modelClassList.size()-1);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
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
                    }

                });
    }

}