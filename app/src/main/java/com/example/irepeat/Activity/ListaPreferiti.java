package com.example.irepeat.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.irepeat.Adapter.QuizAdapter;
import com.example.irepeat.Bean.QuizBean;
import com.example.irepeat.DAO.DBHelper;
import com.example.irepeat.DAO.QuizDAO;
import com.example.irepeat.R;

import java.util.ArrayList;

public class ListaPreferiti extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.lista_preferiti_landscape);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.lista_preferiti);
        }

        ArrayList<QuizBean> listaQuiz;
        QuizDAO dao = new QuizDAO(new DBHelper(getApplicationContext()));

        dao.open();
        listaQuiz = dao.selectAll();
        dao.close();

        ArrayList<QuizBean> listaQuizPref = null;

        for (QuizBean q:listaQuiz) {
            if(q.getPreferito() == 1)
                listaQuizPref.add(q);
        }

        ListView listView = findViewById(R.id.listaQuiz);
        QuizAdapter adapter = new QuizAdapter(getApplicationContext(), R.layout.list_element_quiz, listaQuizPref);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void onClickHomepage(View v){
        Intent i= new Intent(this, Homepage.class);
        startActivity(i);
    }

    public void onClickProfilo(View v){
        Intent i= new Intent(this, ProfiloPersonale.class);
        startActivity(i);
    }

}
