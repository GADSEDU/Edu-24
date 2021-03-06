package com.example.edu24.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.edu24.NetworkUtils;
import com.example.edu24.R;
import com.example.edu24.ClassRoomActivity;
import com.example.edu24.model.User;
import com.example.edu24.util.LoginSharePref;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class AccountFragment extends Fragment {
    private static final String TAG = "firebase";
    private static final int RC_SIGN_IN = 1;
    public static final int PICTURE_RESULT = 42;
    private Button signUpButton,signInButton,googleButton;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseStorage storage;
    private StorageReference storeRef;
    private FirebaseAuth auth;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount mGoogleSignInAccount;
    private ProgressBar progressBar;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        //Initializing instances
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        storeRef = storage.getReference().child(getString(R.string.db_profile));
        databaseReference = firebaseDatabase.getReference().child(getString(R.string.db_users));
        //Initialize Views
        signUpButton = view.findViewById(R.id.acct_sign_up_button);
        signInButton = view.findViewById(R.id.acctEmail);
        googleButton = view.findViewById(R.id.acctGoogle);
        progressBar = view.findViewById(R.id.progressBar);
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(),gso);
        FirebaseUser user = auth.getCurrentUser();
        updateUI(user);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signUpButton.setOnClickListener(view1 -> NavHostFragment.findNavController(AccountFragment.this)
                .navigate(R.id.action_accountFragment_to_signUpFragment));
        signInButton.setOnClickListener(view12 -> NavHostFragment.findNavController(AccountFragment.this)
                .navigate(R.id.action_accountFragment_to_signInFragment));
        googleButton.setOnClickListener(view13 -> {
            if (NetworkUtils.isNetworkConnected(getContext())){
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }else {
                Snackbar.make(view13, "Internet connection failed", Snackbar.LENGTH_LONG)    .show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                Log.d(TAG, "firebaseAuthWithGoogle:" + account );
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getIdToken());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.d(TAG, "Google sign in failed", e);
                // ...
            }
        }
        if (requestCode == PICTURE_RESULT && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            storeRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                }
            });
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        progressBar.setVisibility(View.VISIBLE);
        requireActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            mGoogleSignInAccount = GoogleSignIn.getLastSignedInAccount(getActivity());
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
                            if (mGoogleSignInAccount != null){
                                String personGivenName = mGoogleSignInAccount.getGivenName();
                                String personFamilyName = mGoogleSignInAccount.getFamilyName();
                                String personEmail = mGoogleSignInAccount.getEmail();
                                Uri personPhoto = mGoogleSignInAccount.getPhotoUrl();
                                storeRef.putFile(personPhoto);
                                String userID = auth.getCurrentUser().getUid();
                                databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists()){
                                            Log.d("Users", "User already exist");
                                        }else{
                                            saveUser(personGivenName,personFamilyName,personEmail,personPhoto.toString());
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                            updateUI(user);
                            progressBar.setVisibility(View.GONE);
                            requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        } else {
                            // If sign in fails, display a message to the user.
                            progressBar.setVisibility(View.GONE);
                            requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Log.d(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }

                    }
                });
    }

    private void saveUser(String firstName,String surname, String email, String image) {
        String userID = auth.getCurrentUser().getUid();
        User user = new User(userID,firstName,surname,email,image);
        databaseReference.child(userID).setValue(user);
    }
    @Override
    public void onResume() {
        super.onResume();
        FirebaseUser user = auth.getCurrentUser();
        updateUI(user);
    }

    private void updateUI(FirebaseUser user) {
        if(user != null){
            startActivity(new Intent(getContext(), ClassRoomActivity.class));
            getActivity().finish();
        }else {
            Toast.makeText(getContext(), "please sign in to continue",
                    Toast.LENGTH_SHORT).show();
        }
    }
}