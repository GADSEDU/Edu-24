package com.example.edu24.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.edu24.R;
import com.example.edu24.ClassRoomActivity;
import com.example.edu24.util.LoginSharePref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignInFragment extends Fragment {
    private EditText email,password;
    private Button SignIn;
    private FirebaseAuth auth;
    private static final String TAG = "signIn";
    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private LoginSharePref loginSharePref;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        progressBar = view.findViewById(R.id.sign_in_progressBar);
        loginSharePref = new LoginSharePref(getContext());
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Users");
        email = view.findViewById(R.id.sign_in_email);
        password = view.findViewById(R.id.sign_in_password);
        SignIn = view.findViewById(R.id.sign_in_button);
//        authStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = auth.getCurrentUser();
//                if (user != null){
//                    updateUI(user);
//                }else {
//                    Log.d(TAG, "onAuthStateChanged: " + "NotSigned in");
//                    updateUI(null);
//                }
//            }
//        };

        FirebaseUser user = auth.getCurrentUser();
        updateUI(user);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();
                if (!TextUtils.isEmpty(emailText) && !TextUtils.isEmpty(passwordText)){
                    auth.signInWithEmailAndPassword(emailText,passwordText)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        progressBar.setVisibility(View.VISIBLE);
                                        requireActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        FirebaseUser user = auth.getCurrentUser();
                                        updateUI(user);
                                        progressBar.setVisibility(View.GONE);
                                        requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        updateUI(null);
                                        progressBar.setVisibility(View.GONE);
                                        requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        Toast.makeText(getContext(), "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: " + e.getMessage());
                            progressBar.setVisibility(View.GONE);
                            requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(getContext(), "Authentication failed."  + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if(user != null){
            startActivity(new Intent(getContext(), ClassRoomActivity.class));
            getActivity().finish();
        }else {
            Toast.makeText(getContext(), "Enter valid Username and Password",
                    Toast.LENGTH_SHORT).show();
        }
    }
}