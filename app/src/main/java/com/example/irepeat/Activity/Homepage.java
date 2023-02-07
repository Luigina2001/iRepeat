package com.example.irepeat.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.irepeat.R;

public class Homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.homepage_landscape);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.homepage);
        }
    }

    public void onClickCreaNuovoQuiz(View v){
        Intent i= new Intent(this, CreazioneQuiz.class);
        startActivity(i);
    }

    public void onClickRicerca(View v){
        Intent i= new Intent(this, Ricerca.class);
        startActivity(i);
    }

    public void onClickListaPreferiti(View v){
        Intent i= new Intent(this, ListaPreferiti.class);
        startActivity(i);
    }

    public void onClickProfilo(View v){
        // se l'utente è loggato
        Intent i= new Intent(this, ProfiloPersonale.class);
        startActivity(i);

        //altrimenti
        //Intent i= new Intent(this, Login.class);
        //startActivity(i);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
