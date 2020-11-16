package com.example.edu24.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.edu24.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ClassworkChoiceFragment extends BottomSheetDialogFragment {

    public ClassworkChoiceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_classwork_choice, container, false);
    }
}