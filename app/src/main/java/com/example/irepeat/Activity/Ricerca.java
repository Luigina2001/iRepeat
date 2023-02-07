package com.example.irepeat.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.irepeat.R;

public class Ricerca extends AppCompatActivity {

    String filtro="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null){
            filtro=savedInstanceState.getString("filtro");
        }

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.ricerca_quiz_landscape);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.ricerca_quiz);
        }

        Toast.makeText(this, filtro, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("filtro", filtro);
        super.onSaveInstanceState(outState);
    }

    public void onClickHomepage (View view){
        Intent i= new Intent(this, Homepage.class);
        startActivity(i);
    }

    public void onClickListaPreferiti(View v){
        Intent i= new Intent(this, ListaPreferiti.class);
        startActivity(i);
    }

    public void onClickProfilo(View v){
        //se l'utente è loggato
        Intent i= new Intent(this, ProfiloPersonale.class);
        startActivity(i);

        //altrimenti
        //Intent i= new Intent(this, Login.class);
        //startActivity(i);
    }

    public void onClickRicerca (View v){
        TextView testoRicerca= (TextView) findViewById(R.id.testoRicerca);

        // effettuare ricerca tenendo conto anche del filtro
        // se filtro=="" ricerca per nome
    }

    public void onClickFiltroNome(View v){
        filtro= "nome";
    }

    public void onClickFiltroDisciplina(View v){
        filtro= "disciplina";
    }
}
