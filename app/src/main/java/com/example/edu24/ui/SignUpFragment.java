package com.example.edu24.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.edu24.ClassRoomActivity;
import com.example.edu24.R;
import com.example.edu24.model.User;
import com.example.edu24.util.LoginSharePref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUpFragment extends Fragment {
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ProgressBar progressBar;
    private TextInputEditText firstname, surname,email,password,confirmedPassword;
    private TextInputLayout passwordLayout, confirmedPasswordLayout;
    private Button SignUpButton;
    private LoginSharePref loginSharePref;
    private static final String TAG = "signUp";
    private FirebaseUser currentUser;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        loginSharePref = new LoginSharePref(getContext());
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child(getString(R.string.db_users));
        //Initializing the views
        progressBar = view.findViewById(R.id.sign_up_progressBar);
        firstname = view.findViewById(R.id.sign_up_firstname);
        surname = view.findViewById(R.id.sign_up_surname);
        email = view.findViewById(R.id.sign_up_email);
        password = view.findViewById(R.id.sign_up_password);
        confirmedPassword = view.findViewById(R.id.sign_up_confirm_password);
        SignUpButton = view.findViewById(R.id.sign_up_button);
        passwordLayout = view.findViewById(R.id.sign_up_passwordLayout);
        confirmedPasswordLayout = view.findViewById(R.id.sign_up_confirm_passwordLayout);
        SignUpButton = view.findViewById(R.id.sign_up_button);
        //Setting toolbar
        Toolbar myToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(myToolbar);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = email.getText().toString();
                String firstnameText = firstname.getText().toString();
                String surnameText = surname.getText().toString();
                String passwordText = password.getText().toString();
                String confirmedPasswordText = confirmedPassword.getText().toString();
                if (!TextUtils.isEmpty(emailText) && !TextUtils.isEmpty(firstnameText)
                        && !TextUtils.isEmpty(surnameText)
                        && !TextUtils.isEmpty(passwordText) && !TextUtils.isEmpty(confirmedPasswordText))
                {
                    if (passwordRequirement(passwordText)){
                        passwordLayout.setErrorEnabled(false);
                        if (Objects.equals(confirmedPasswordText, passwordText)){
                            confirmedPasswordLayout.setErrorEnabled(false);
                            passwordLayout.setErrorEnabled(false);
                            progressBar.setVisibility(View.VISIBLE);
                            requireActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            signUp(emailText,passwordText,firstnameText, surnameText);
                            progressBar.setVisibility(View.GONE);
                            requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        }else{
                            confirmedPasswordLayout.setErrorEnabled(true);
                            confirmedPasswordLayout.setErrorIconDrawable(0);
                            confirmedPasswordLayout.setError("Password not the same");
                        }
                    }else{
                        passwordLayout.setErrorEnabled(true);
                        passwordLayout.setErrorIconDrawable(0);
                        passwordLayout.setError("Use a minimum of 8 characters with a mix of letters and numbers");
                    }
                }else {
                    Snackbar.make(view,"Fill All Fields", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signUp(final String email, String password, final String firstname, final String surname) {
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            currentUser = auth.getCurrentUser();
                            saveUser(firstname,surname,email);
                            updateUI(currentUser);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }
        });
    }

    private void saveUser(String firstname, String surname, String email) {
        String userID = auth.getCurrentUser().getUid();
        User user = new User(userID,firstname,surname,email,"");
        databaseReference.child(userID).setValue(user);
    }

    private boolean passwordRequirement(String s) {
        String n = ".*[0-9].*";
        String a = ".*[a-z].*";
        return s.matches(n) && s.matches(a);
    }

    private void updateUI(FirebaseUser user) {
        if(user != null){
            startActivity(new Intent(getContext(), ClassRoomActivity.class));
            getActivity().finish();
        }else {

        }
    }
}