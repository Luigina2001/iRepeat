package com.example.irepeat.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.irepeat.Bean.UtenteBean;

public class MyPreferences {
    private static final String MY_PREFERENCES = "my_preferences";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String EMAIL = "email";

    private SharedPreferences sharedPreferences;

    public MyPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void setLoggedIn(boolean isLoggedIn, String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.putString(EMAIL, email);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getEmail(){
        return sharedPreferences.getString(EMAIL, "");

    }

}

