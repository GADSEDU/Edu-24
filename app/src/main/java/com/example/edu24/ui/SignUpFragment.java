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

import com.example.edu24.AccountActivity;
import com.example.edu24.R;
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
    private TextInputEditText firstname,lastname,email,password,confirmedPassword;
    private TextInputLayout firstnameLayout,lastnameLayout, emailLayout, passwordLayout, confirmedPasswordLayout;
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
        databaseReference = firebaseDatabase.getReference().child("Users");
        //Initializing the views
        progressBar = view.findViewById(R.id.sign_up_progressBar);
        firstname = view.findViewById(R.id.sign_up_firstname);
        lastname = view.findViewById(R.id.sign_up_lastname);
        email = view.findViewById(R.id.sign_up_email);
        password = view.findViewById(R.id.sign_up_password);
        confirmedPassword = view.findViewById(R.id.sign_up_confirm_password);
        SignUpButton = view.findViewById(R.id.sign_up_button);
        firstnameLayout = view.findViewById(R.id.sign_up_first_nameLayout);
        lastnameLayout = view.findViewById(R.id.sign_up_last_nameLayout);
        emailLayout = view.findViewById(R.id.sign_up_emailLayout);
        passwordLayout = view.findViewById(R.id.sign_up_passwordLayout);
        confirmedPasswordLayout = view.findViewById(R.id.sign_up_confirm_passwordLayout);
        SignUpButton = view.findViewById(R.id.sign_up_button);
        //Setting toolbar
        Toolbar myToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);

        //firebase auth
//        authStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                currentUser = firebaseAuth.getCurrentUser();
//                if (currentUser != null){
//                    updateUI(currentUser);
//                }else {
//                    Log.d(TAG, "onAuthStateChanged: " + "NotSigned in");
//                    updateUI(null);
//                }
//            }
//        };
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
                String lastnameText = lastname.getText().toString();
                String passwordText = password.getText().toString();
                String confirmedPasswordText = confirmedPassword.getText().toString();
                if (!TextUtils.isEmpty(emailText) && !TextUtils.isEmpty(firstnameText)
                        && !TextUtils.isEmpty(lastnameText)
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
                            signUp(emailText,passwordText,firstnameText, lastnameText);
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

    private void signUp(String email, String password, String firstname, String lastname) {
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            currentUser = auth.getCurrentUser();
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

    private boolean passwordRequirement(String s) {
        String n = ".*[0-9].*";
        String a = ".*[a-z].*";
        return s.matches(n) && s.matches(a);
    }

    private void updateUI(FirebaseUser user) {
        if(user != null){
            loginSharePref.setIsLogin(true);
            startActivity(new Intent(getContext(), AccountActivity.class));
            getActivity().finish();
        }else {
            Toast.makeText(getContext(), "please sign up to continue",
                    Toast.LENGTH_SHORT).show();
        }
    }
}