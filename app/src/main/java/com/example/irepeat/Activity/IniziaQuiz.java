package com.example.irepeat.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.irepeat.Adapter.OpzioniRispostaAdapter;
import com.example.irepeat.Bean.DomandaBean;
import com.example.irepeat.Bean.QuizBean;
import com.example.irepeat.Bean.RispostaBean;
import com.example.irepeat.DAO.DBHelper;
import com.example.irepeat.DAO.QuizDAO;
import com.example.irepeat.R;

import java.util.ArrayList;
import java.util.HashMap;

public class IniziaQuiz extends AppCompatActivity {

    private ArrayList<DomandaBean> domande;
    private HashMap<DomandaBean, ArrayList<RispostaBean>> risposte= new HashMap<>();
    private TextView numeroDomanda, testoDomanda, numeroDomandaOmbra;
    private int count=0;
    private OpzioniRispostaAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.inizia_quiz_landscape);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.inizia_quiz);
        }

        Intent i=getIntent();
        int id=i.getIntExtra("id", -1);

        Log.d("MYDEBUG INIZIA QUIZ", "id quiz= "+id);

        QuizDAO quizDAO= new QuizDAO(new DBHelper(this));
        quizDAO.open();
        QuizBean quizBean= quizDAO.select(id);
        quizDAO.close();

        quizBean.setDomande(new DBHelper(this));
        domande=quizBean.getDomande();

        for (DomandaBean d: domande){
            d.setRisposte(new DBHelper(this));
            risposte.put(d, d.getRisposte());
        }

        numeroDomanda= findViewById(R.id.numeroDomanda);
        numeroDomandaOmbra= findViewById(R.id.numeroDomandaOmbra);
        numeroDomanda.setText("Domanda n."+(count+1));
        numeroDomandaOmbra.setText("Domanda n."+(count+1));

        testoDomanda= findViewById(R.id.testoDomanda);
        testoDomanda.setText(domande.get(0).getTesto());

        listView= findViewById(R.id.opzioniRisposte);
        adapter = new OpzioniRispostaAdapter(this, R.layout.list_element_opzioni_risposta, risposte.get(domande.get(0)));
        for (RispostaBean r: risposte.get(domande.get(0))){
            Log.d("MYDEBUG_INIZIA_QUIZ", r.getTesto());
        }
        listView.setAdapter(adapter);

    }

    public void onClickTerminaQuiz (View v){
        //messaggio di conferma??

        Intent i= new Intent(this, EsitoQuiz.class);
        startActivity(i);
    }

    public void onClickPrecedente (View v){

    }

    public void onClickSuccessiva (View v){
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
