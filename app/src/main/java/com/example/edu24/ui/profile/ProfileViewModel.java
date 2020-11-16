package com.example.edu24.ui.profile;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {
    private String firstName,Surname,email;
    private Uri imageProfile;

    public ProfileViewModel(String firstName, String surname, String email, Uri imageProfile) {
        this.firstName = firstName;
        Surname = surname;
        this.email = email;
        this.imageProfile = imageProfile;
    }
}
