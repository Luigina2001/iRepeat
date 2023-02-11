package com.example.irepeat.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.irepeat.Bean.UtenteBean;

public class MyPreferences {
    private static final String MY_PREFERENCES = "my_preferences";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String ID = "id";

    private SharedPreferences sharedPreferences;

    public MyPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void setLoggedIn(boolean isLoggedIn, int id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.putString(ID, String.valueOf(id));
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getId(){
        return sharedPreferences.getString(ID, "");

    }

}

