package com.example.irepeat.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.irepeat.Adapter.OpzioniRispostaAdapter;
import com.example.irepeat.Bean.DomandaBean;
import com.example.irepeat.Bean.QuizBean;
import com.example.irepeat.Bean.RispostaBean;
import com.example.irepeat.DAO.DBHelper;
import com.example.irepeat.DAO.QuizDAO;
import com.example.irepeat.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class IniziaQuiz extends AppCompatActivity {

    private ArrayList<DomandaBean> domande;
    private HashMap<DomandaBean, ArrayList<RispostaBean>> risposte= new HashMap<>();
    private HashMap<DomandaBean, RispostaBean> risposteDate= new HashMap<>();
    private TextView numeroDomanda, testoDomanda, numeroDomandaOmbra, tempoRimasto;
    private int count=0;
    private OpzioniRispostaAdapter adapter;
    private ListView listView;
    private int id=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.inizia_quiz_landscape);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.inizia_quiz);
        }

        if (savedInstanceState!=null){
            count=savedInstanceState.getInt("count");
            id=savedInstanceState.getInt("id");
            risposteDate= (HashMap<DomandaBean, RispostaBean>) savedInstanceState.getSerializable("risposteDate");
            for (RispostaBean r: risposteDate.values()){
                if (r!=null) {
                    Log.d("MYDEBUG_RISPOSTE", r.toString());
                }
            }
        }
        else{
            Intent i=getIntent();
            id=i.getIntExtra("id", -1);
        }


        Log.d("MYDEBUG INIZIA QUIZ", "id quiz= "+id);

        QuizDAO quizDAO= new QuizDAO(new DBHelper(this));
        quizDAO.open();
        QuizBean quizBean= quizDAO.select(id);
        quizDAO.close();

        tempoRimasto= findViewById(R.id.tempoRimasto);
        tempoRimasto.setText(quizBean.getDurata());

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
        testoDomanda.setText(domande.get(count).getTesto());

        listView= findViewById(R.id.opzioniRisposte);
        ArrayList<RispostaBean> opzioniDiRisposta= risposte.get(domande.get(count));
        Collections.shuffle(opzioniDiRisposta, new Random());
        adapter = new OpzioniRispostaAdapter(this, R.layout.list_element_opzioni_risposta, opzioniDiRisposta, this);
        for (RispostaBean r: risposte.get(domande.get(count))){
            Log.d("MYDEBUG_INIZIA_QUIZ", r.getTesto());
        }
        listView.setAdapter(adapter);

        if (risposteDate.get(domande.get(count))!=null){
            int i=0;
            for (RispostaBean r: risposte.get(domande.get(count))){
                if (r.equals(risposteDate.get(domande.get(count))))
                    break;
                i++;
            }
            adapter.setSelectedPosition(i);
        }

    }

    public void onClickTerminaQuiz (View v){

        new AlertDialog.Builder(this)
                .setMessage("Sei sicuro di voler terminare il quiz?")
                .setCancelable(false)
                .setPositiveButton("Sì", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i= new Intent(getApplicationContext(), EsitoQuiz.class);
                        i.putExtra("risposteDate", risposteDate);
                        startActivity(i);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void onClickPrecedente (View v){
        if (--count >=0) {
            numeroDomanda.setText("Domanda n." + (count + 1));
            numeroDomandaOmbra.setText("Domanda n." + (count + 1));

            testoDomanda = findViewById(R.id.testoDomanda);
            testoDomanda.setText(domande.get(count).getTesto());

            listView = findViewById(R.id.opzioniRisposte);
            ArrayList<RispostaBean> opzioniDiRisposta= risposte.get(domande.get(count));
            Collections.shuffle(opzioniDiRisposta, new Random());
            adapter = new OpzioniRispostaAdapter(this, R.layout.list_element_opzioni_risposta, opzioniDiRisposta, this);
            for (RispostaBean r : risposte.get(domande.get(count))) {
                Log.d("MYDEBUG_INIZIA_QUIZ", r.getTesto());
            }
            listView.setAdapter(adapter);
            if (risposteDate.get(domande.get(count))!=null){
                int i=0;
                for (RispostaBean r: risposte.get(domande.get(count))){
                    if (r.equals(risposteDate.get(domande.get(count))))
                        break;
                    i++;
                }
                adapter.setSelectedPosition(i);
            }
        }
        else{
            count++;
            Toast.makeText(this, "Questa è la prima domanda", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickSuccessiva (View v){
        if (++count < domande.size()) {

            numeroDomanda.setText("Domanda n." + (count + 1));
            numeroDomandaOmbra.setText("Domanda n." + (count + 1));

            testoDomanda = findViewById(R.id.testoDomanda);
            testoDomanda.setText(domande.get(count).getTesto());

            listView = findViewById(R.id.opzioniRisposte);
            ArrayList<RispostaBean> opzioniDiRisposta= risposte.get(domande.get(count));
            Collections.shuffle(opzioniDiRisposta, new Random());
            adapter = new OpzioniRispostaAdapter(this, R.layout.list_element_opzioni_risposta, opzioniDiRisposta, this);
            for (RispostaBean r : risposte.get(domande.get(count))) {
                Log.d("MYDEBUG_INIZIA_QUIZ", r.getTesto());
            }
            listView.setAdapter(adapter);
            if (risposteDate.get(domande.get(count))!=null){
                int i=0;
                for (RispostaBean r: risposte.get(domande.get(count))){
                    if (r.equals(risposteDate.get(domande.get(count))))
                        break;
                    i++;
                }
                adapter.setSelectedPosition(i);
            }
        }
        else{
            count--;
            Toast.makeText(this, "Questa è l'ultima domanda", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("count", count);
        outState.putInt("id", id);
        outState.putSerializable("risposteDate", risposteDate);
        super.onSaveInstanceState(outState);
    }

    public void salvaRisposta(int selected){
        if (selected<0){
            risposteDate.put(domande.get(count), null);
        }
        else {
            ArrayList<RispostaBean> risposteDomanda= risposte.get(domande.get(count));
            RispostaBean rispostaData= risposteDomanda.get(selected);
            if (risposteDate.get(domande.get(count))!=null){
                risposteDate.remove(domande.get(count));
            }
            risposteDate.put(domande.get(count), rispostaData);
        }
    }
}
