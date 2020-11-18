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

import com.example.edu24.R;
import com.example.edu24.ResourcesActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ClassworkChoiceFragment extends BottomSheetDialogFragment {
    private Button createAss,createQuiz,postMat;

    public ClassworkChoiceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_classwork_choice, container, false);

        createAss = root.findViewById(R.id.create_assignment);
        createQuiz = root.findViewById(R.id.create_quiz);
        postMat = root.findViewById(R.id.post_material);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createAss.setOnClickListener(v -> {
            getActivity().startActivity(new Intent(getContext(), ResourcesActivity.class));
            getDialog().cancel();
        });
        createQuiz.setOnClickListener(v -> {
            getActivity().startActivity(new Intent(getContext(), ResourcesActivity.class));
            getDialog().cancel();
        });
        createQuiz.setOnClickListener(v -> {

        });
    }
}