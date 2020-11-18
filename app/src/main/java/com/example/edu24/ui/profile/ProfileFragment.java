package com.example.edu24.ui.profile;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.edu24.NetworkUtils;
import com.example.edu24.R;
import com.example.edu24.model.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private static final String TAG = "Profile";
    private CircleImageView profile_image;
    private TextInputEditText first_name,surname,email;
    private DatabaseReference userRef;
    private Button update;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private FirebaseStorage storage;
    private StorageReference storeRef;
    private ProfileViewModel profileViewModel;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        first_name = root.findViewById(R.id.update_firstname);
        surname = root.findViewById(R.id.update_surname);
        email = root.findViewById(R.id.update_email);
        profile_image = root.findViewById(R.id.profile_image);
        update = root.findViewById(R.id.update_button);
        //Firebase Instance
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storeRef = storage.getReference().child(getString(R.string.db_profile));

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getUserAccountData();
        update.setOnClickListener(view1 -> {
            if (NetworkUtils.isNetworkConnected(getContext())){
                String userID = auth.getCurrentUser().getUid();
                User user = new User();
                user.setUser_first_name(first_name.getText().toString());
                user.setUser_surname(surname.getText().toString());
//                storeRef.putFile();
                userRef.child(userID).setValue(user);
            }else {
                Snackbar.make(view, "Internet connection failed", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void getUserAccountData() {
        Query query = userRef.child(getString(R.string.db_users))
                .orderByKey()
                .equalTo(auth.getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot singleDataSnapshot: snapshot.getChildren()){
                    User user = singleDataSnapshot.getValue(User.class);
                    Log.d(TAG, "onDataChange: " + user.toString());
                    first_name.setText(user.getUser_first_name());
                    surname.setText(user.getUser_surname());
                    email.setText(user.getUser_email());
                    if (user.getProfile_image() != null){
                        Picasso.get().load(Uri.parse(user.getProfile_image()))
                                .into(profile_image);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}