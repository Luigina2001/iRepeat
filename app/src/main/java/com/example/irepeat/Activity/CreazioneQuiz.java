package com.example.irepeat.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.irepeat.R;

public class CreazioneQuiz extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.creazione_quiz_landscape);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.creazione_quiz);
        }
    }

    public void onClickBack(View view){
        super.onBackPressed();
    }

    public void onClickCreaQuiz(View view){
        Intent i= new Intent(this, Homepage.class);
        startActivity(i);
    }

    public void onClickAggiungiDomanda(View view){
        //aggiungere elemento ad adapeter listViewCreazioneDomande
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
