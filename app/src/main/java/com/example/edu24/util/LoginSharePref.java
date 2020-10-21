package com.example.edu24.util;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginSharePref {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    // shared pref mode
    int PRIVATE_MODE = 0;
    // Shared preferences file name
    private static final String PREF_NAME = "com.example.edu24";
    private static final String IS_LOGIN = "IsLogin";

    public LoginSharePref(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    public void setIsLogin(boolean isFirstTime) {
        editor.putBoolean(IS_LOGIN, isFirstTime);
        editor.commit();
    }

    public boolean isLogin() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
