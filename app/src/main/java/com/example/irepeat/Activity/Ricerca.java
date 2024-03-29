package com.example.irepeat.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.irepeat.Adapter.RicercaQuizAdapter;
import com.example.irepeat.Bean.QuizBean;
import com.example.irepeat.DAO.DBHelper;
import com.example.irepeat.DAO.QuizDAO;
import com.example.irepeat.R;

import java.util.ArrayList;

public class Ricerca extends AppCompatActivity {

    String filtro="";
    SearchView searchView;
    Button nome;
    Button disciplina;
    ListView listView;
    RicercaQuizAdapter adapter;
    String testoRicerca="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        if (savedInstanceState!=null){
            filtro=savedInstanceState.getString("filtro");
            testoRicerca = savedInstanceState.getString("testoRicerca");
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


        //Toast.makeText(this, filtro, Toast.LENGTH_LONG).show();

        searchView = findViewById(R.id.testoRicerca);
        searchView.setQuery(testoRicerca, false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(getApplicationContext(), query, Toast.LENGTH_LONG).show();

                ArrayList<QuizBean> quizRicerca;
                QuizDAO dao = new QuizDAO(new DBHelper(getApplicationContext()));


                if(filtro.equalsIgnoreCase("") || filtro.equalsIgnoreCase("nome")){
                    dao.open();
                    quizRicerca = dao.selectByNome(query);
                    dao.close();
                }else{
                    dao.open();
                    quizRicerca = dao.selectByDisciplina(query);
                    dao.close();
                }

                ListView listView = (ListView)findViewById(R.id.quizList);
                if(quizRicerca!=null) {
                    RicercaQuizAdapter adapter = new RicercaQuizAdapter(getApplicationContext(), R.layout.list_element_ricerca_quiz, quizRicerca);
                    listView.setAdapter(adapter);
                }
                else{
                    Toast.makeText(getApplicationContext(), "NESSUN QUIZ TROVATO", Toast.LENGTH_LONG).show();
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //Toast.makeText(getApplicationContext(), query, Toast.LENGTH_LONG).show();

                ArrayList<QuizBean> quizRicerca;
                QuizDAO dao = new QuizDAO(new DBHelper(getApplicationContext()));


                if(filtro.equalsIgnoreCase("") || filtro.equalsIgnoreCase("nome")){
                    dao.open();
                    quizRicerca = dao.selectByNome(query);
                    dao.close();
                }else{
                    dao.open();
                    quizRicerca = dao.selectByDisciplina(query);
                    dao.close();
                }

                ListView listView = (ListView)findViewById(R.id.quizList);
                if(quizRicerca!=null) {
                    RicercaQuizAdapter adapter = new RicercaQuizAdapter(getApplicationContext(), R.layout.list_element_ricerca_quiz, quizRicerca);
                    listView.setAdapter(adapter);
                }

                return true;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("filtro", filtro);
        outState.putString("testoRicerca", searchView.toString());
        super.onSaveInstanceState(outState);
    }

    public void onClickHomepage (View view){
        Intent i= new Intent(this, Homepage.class);
        startActivity(i);
    }

    public void onClickListaPreferiti(View v){
        //se l'utente è loggato
        Intent i= new Intent(this, ListaPreferiti.class);
        startActivity(i);

        //altrimenti
        //Intent i= new Intent(this, Login.class);
        //startActivity(i);

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
