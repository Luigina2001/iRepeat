package com.example.irepeat;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.example.irepeat.Bean.DomandaBean;
import com.example.irepeat.Bean.QuizBean;
import com.example.irepeat.Bean.RispostaBean;
import com.example.irepeat.Bean.UtenteBean;
import com.example.irepeat.DAO.DBHelper;
import com.example.irepeat.DAO.DomandaDAO;
import com.example.irepeat.DAO.QuizDAO;
import com.example.irepeat.DAO.RispostaDAO;
import com.example.irepeat.DAO.UtenteDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);


        DBHelper db= new DBHelper(this);

        UtenteDAO utenteDAO= new UtenteDAO(db);
        utenteDAO.open();
        UtenteBean utente= new UtenteBean("luigina@iRepeat.it", "luigina01", "prova bio", "luigina", "costante", "luigina01");
        utenteDAO.insert(utente);

        Log.d("MYDEBUG", utente.toString());
        Log.d("MYDEBUG", String.valueOf(utenteDAO.select(utente.getEmail(), utente.getPassword())));

        utenteDAO.close();

        /*QuizDAO quizDAO = new QuizDAO(db);
        quizDAO.open();
        UtenteBean utente= new UtenteBean("luigina@iRepeat.it", "luigina01", "prova bio", "luigina", "costante", "luigina01");
        QuizBean quiz= new QuizBean("descrizione", "nome", "disciplina", 1, "durata", 0, utente);

        quizDAO.insert(quiz);

        Log.d("MYDEBUG", String.valueOf(quizDAO.select(quiz.getId())));

        quizDAO.close();*/

        /*UtenteBean utente= new UtenteBean("luigina@iRepeat.it", "luigina01", "prova bio", "luigina", "costante", "luigina01");
        QuizDAO quizDAO = new QuizDAO(db);
        quizDAO.open();
        ArrayList<QuizBean> quiz= quizDAO.selectByUtente(utente);
        (quiz.get(0)).setDomande(db);

        Log.d("MYDEBUG", quiz.get(0).toString());
        //Log.d("MYDEBUG", quiz.toString());

        DomandaDAO domandaDAO = new DomandaDAO(db);
        domandaDAO.open();
        //DomandaBean domanda= new DomandaBean("Testo domanda di prova", quiz);
        //domandaDAO.insert(domanda);
        /*ArrayList<DomandaBean> domande= domandaDAO.selectByQuiz(quiz);
        for (DomandaBean d: domande){
            Log.d("MYDEBUG", d.toString());
        }*/

        /*DomandaBean domanda= (domandaDAO.selectByQuiz(quiz.get(0))).get(0);
        domanda.setRisposte(db);
        Log.d("MYDEBUG", domanda.toString());


        //Log.d("MYDEBUG", ""+domandaDAO.selectByQuiz(quiz.get(0)));

        //RispostaDAO rispostaDAO= new RispostaDAO(db);
        //rispostaDAO.open();
        //RispostaBean risposta= new RispostaBean("testo risposta prova", domanda, 0);
        //rispostaDAO.insert(risposta);

        //Log.d("MYDEBUG", "risposta: "+(rispostaDAO.select(3)).toString());

        //ArrayList<RispostaBean> risposte= rispostaDAO.selectByDomanda(domanda);

        //for (RispostaBean r: risposte)
            //Log.d("MYDEBUG", r.toString());



        //domandaDAO.close();
        //quizDAO.close();
        */
    }
}