package com.example.irepeat.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.irepeat.R;

public class ModificaCredenziali extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.modifica_credenziali_landscape);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.modifica_credenziali);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void onClickBack(View view){
        super.onBackPressed();
    }

    public void onClickModificaCredenziali(View view){

        // gestire la modifica
        Intent i= new Intent(this, Homepage.class);
        startActivity(i);

    }

}
