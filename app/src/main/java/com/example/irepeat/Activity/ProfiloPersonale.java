package com.example.irepeat.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.irepeat.Adapter.QuizAdapter;
import com.example.irepeat.Bean.QuizBean;
import com.example.irepeat.Bean.UtenteBean;
import com.example.irepeat.DAO.DBHelper;
import com.example.irepeat.DAO.QuizDAO;
import com.example.irepeat.DAO.UtenteDAO;
import com.example.irepeat.R;
import com.example.irepeat.Utils.MyPreferences;

import java.util.ArrayList;

public class ProfiloPersonale extends AppCompatActivity {

    private MyPreferences preferences;
    private UtenteBean utente;
    private TextView nickname;
    private GridView gridViewQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView filtro;

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.profilo_personale_landscape);
            filtro= findViewById(R.id.filtro);
            preferences= new MyPreferences(getApplicationContext());

            //inizializzazione menu
            filtro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu menu = new PopupMenu(ProfiloPersonale.this, filtro);

                    menu.getMenuInflater().inflate(R.menu.menu_profilo_landscape, menu.getMenu());
                    menu.setOnMenuItemClickListener(item -> {
                        String filtroStringa = item.getTitle().toString();

                        if (filtroStringa.equalsIgnoreCase("Modifica credenziali")){
                            Intent i= new Intent(ProfiloPersonale.this, ModificaCredenziali.class);
                            startActivity(i);
                        }
                        else if (filtroStringa.equalsIgnoreCase("Crea quiz")){
                            Intent i= new Intent(ProfiloPersonale.this, CreazioneQuiz.class);
                            startActivity(i);
                        }
                        else if (filtroStringa.equalsIgnoreCase("Logout")){
                            preferences.setLoggedIn(false, "");
                            Toast.makeText(getApplicationContext(), "Logout effettuato", Toast.LENGTH_LONG).show();
                            Intent i= new Intent(ProfiloPersonale.this, Homepage.class);
                            startActivity(i);
                        }
                        return true;
                    });
                    menu.show();
                }
            });
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.profilo_personale);
            filtro= findViewById(R.id.filtro);
            preferences= new MyPreferences(getApplicationContext());
            //inizializzazione menu
            filtro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu menu = new PopupMenu(ProfiloPersonale.this, filtro);

                    menu.getMenuInflater().inflate(R.menu.menu_profilo, menu.getMenu());
                    menu.setOnMenuItemClickListener(item -> {
                        String filtroStringa = item.getTitle().toString();

                        if (filtroStringa.equalsIgnoreCase("Modifica credenziali")){
                            Intent i= new Intent(ProfiloPersonale.this, ModificaCredenziali.class);
                            startActivity(i);
                        }
                        else if (filtroStringa.equalsIgnoreCase("Crea quiz")){
                            Intent i= new Intent(ProfiloPersonale.this, CreazioneQuiz.class);
                            startActivity(i);
                        }
                        else if (filtroStringa.equalsIgnoreCase("Logout")){
                            preferences.setLoggedIn(false, "");
                            Toast.makeText(getApplicationContext(), "Logout effettuato", Toast.LENGTH_LONG).show();
                            Intent i= new Intent(ProfiloPersonale.this, Homepage.class);
                            startActivity(i);
                        }
                        return true;
                    });
                    menu.show();
                }
            });
        }


        UtenteDAO utenteDAO= new UtenteDAO(new DBHelper(this));
        utenteDAO.open();
        String email= preferences.getEmail();
        utente= utenteDAO.doRetrieveByEmail(email);
        utenteDAO.close();
        nickname= findViewById(R.id.nicknameProfilo);
        nickname.setText(utente.getNickname());

        gridViewQuiz= findViewById(R.id.gridViewQuiz);
        QuizDAO quizDAO= new QuizDAO(new DBHelper(this));
        quizDAO.open();
        ArrayList<QuizBean> quiz= quizDAO.selectByUtente(utente);
        quizDAO.close();

        if(quiz!=null) {
            for (QuizBean quizBean : quiz)
                quizBean.setDomande(new DBHelper(this));
            Log.d("MYDEBUG", quiz.toString());

            QuizAdapter adapter= new QuizAdapter(this, R.layout.list_element_quiz, quiz);
            gridViewQuiz.setAdapter(adapter);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void onClickHomepage(View v){
        Intent i= new Intent(this, Homepage.class);
        startActivity(i);
    }

    public void onClickListaPreferiti(View v){
        Intent i= new Intent(this, ListaPreferiti.class);
        startActivity(i);
    }
}
