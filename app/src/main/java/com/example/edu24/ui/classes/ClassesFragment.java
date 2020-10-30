package com.example.edu24.ui.classes;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.edu24.CreateAClassActivity;
import com.example.edu24.JoinActivity;
import com.example.edu24.adapter.ClassAdapter;
import com.example.edu24.model.ModelClass;
import com.example.edu24.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ClassesFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ClassesViewModel mViewModel;
    private List<ModelClass> modelClassList = new ArrayList<>();
    private FloatingActionButton fab;
    private AlertDialog.Builder dialogBuilder;
    private Button create,join;

    public static ClassesFragment newInstance() {
        return new ClassesFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_classes, container, false);
        mRecyclerView = root.findViewById(R.id.class_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        ClassAdapter adapter = new ClassAdapter(modelClassList,getContext());
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        fab = root.findViewById(R.id.class_fab);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(ClassesViewModel.class);
        // TODO: Use the ViewModel
    }

}