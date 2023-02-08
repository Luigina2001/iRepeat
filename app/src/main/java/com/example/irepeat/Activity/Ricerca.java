package com.example.irepeat.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.irepeat.R;

public class Ricerca extends AppCompatActivity {

    String filtro="";
    SearchView searchView;
    Button nome;
    Button disciplina;

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

        nome= findViewById(R.id.filtroNome);
        disciplina= findViewById(R.id.filtroDisciplina);

        if (filtro.equalsIgnoreCase("") || filtro.equalsIgnoreCase("nome")){
            nome.setTextColor(Color.parseColor("#000000"));
            disciplina.setTextColor(Color.parseColor("#777777"));
        }
        else{
            disciplina.setTextColor(Color.parseColor("#000000"));
            nome.setTextColor(Color.parseColor("#777777"));
        }


        Toast.makeText(this, filtro, Toast.LENGTH_LONG).show();

        searchView = findViewById(R.id.testoRicerca);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(), query, Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Aggiornare i risultati in base alla stringa di ricerca
                return false;
            }
        });
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

    public void onClickFiltroNome(View v){
        filtro= "nome";
        nome.setTextColor(Color.parseColor("#000000"));
        disciplina.setTextColor(Color.parseColor("#777777"));
    }

    public void onClickFiltroDisciplina(View v){
        filtro= "disciplina";
        disciplina.setTextColor(Color.parseColor("#000000"));
        nome.setTextColor(Color.parseColor("#777777"));
    }

    public void onClickAnnulla(View v){
        searchView.setQuery("", false);
    }

}
