package com.example.irepeat.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.irepeat.R;

public class Login extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.login_landscape);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.login_landscape);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void onClickLogin (View view){
        //if login ok --> messaggio login ok
        Intent i= new Intent(this, Homepage.class);
        startActivity(i);

        //altrimenti messaggio di errore e rimango nella stessa activity
    }

    public void onClickRegistrazione (View view){
        Intent i= new Intent(this, Registrazione.class);
        startActivity(i);
    }

    public void onClickBack(View view){
        super.onBackPressed();
    }
}
