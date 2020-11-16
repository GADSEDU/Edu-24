package com.example.edu24.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.edu24.CreateAClassActivity;
import com.example.edu24.JoinActivity;
import com.example.edu24.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ClassChoiceFragment extends BottomSheetDialogFragment {
    Button createClass,joinClass;
    public ClassChoiceFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_class_choice, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createClass = view.findViewById(R.id.create_class);
        joinClass = view.findViewById(R.id.join_class);

        createClass.setOnClickListener(view1 -> {
            startActivity(new Intent(getContext(), CreateAClassActivity.class));
            getDialog().cancel();
        });

        joinClass.setOnClickListener(view2 -> {
            startActivity(new Intent(getContext(), JoinActivity.class));
            getDialog().cancel();
        });
    }
}