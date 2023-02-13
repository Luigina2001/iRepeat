package com.example.irepeat.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.irepeat.Adapter.DomandaRispostaCorrettaAdapter;
import com.example.irepeat.Bean.DomandaBean;
import com.example.irepeat.Bean.QuizBean;
import com.example.irepeat.Bean.RispostaBean;
import com.example.irepeat.DAO.DBHelper;
import com.example.irepeat.DAO.QuizDAO;
import com.example.irepeat.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EsitoQuiz extends AppCompatActivity {

    private HashMap<DomandaBean, RispostaBean> risposteDate= new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.esito_quiz_landscape);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.esito_quiz);
        }

        Intent i= getIntent();
        risposteDate= (HashMap<DomandaBean, RispostaBean>) i.getExtras().getSerializable("risposteDate");
        for (RispostaBean r: risposteDate.values()){
            if (r!=null) {
                Log.d("MYDEBUG", r.toString());
            }
        }

        List<DomandaBean> risposteSbagliate = null;
        int index, sbagliate = 0;


        for (RispostaBean r: risposteDate.values()){
            index = 0;
            if (r.getCorretta() == 0) {
                risposteSbagliate.add(risposteDate.get(i).getDomanda());
                sbagliate++;
            }
            index++;
        }

        int corrette = risposteDate.size() - sbagliate;

        //prendere numero di risposte corrette e sbagliate e tempo impiegato
        TextView tempoImpiegato = findViewById(R.id.tempoImpiegato);
        TextView numDomandeSbagliate = findViewById(R.id.numeroDomandeErrate);
        TextView numDomandeSbagliateOmbra = findViewById(R.id.numeroDomandeErrateOmbra);
        TextView numDomandeCorrette = findViewById(R.id.numeroDomandeCorrette);

        numDomandeSbagliate.append(Integer.toString(sbagliate));
        numDomandeSbagliateOmbra.append(Integer.toString(sbagliate));
        numDomandeCorrette.append(Integer.toString(corrette));

        ListView listView = findViewById(R.id.listViewDomandeErrate);
        DomandaRispostaCorrettaAdapter adapter = new DomandaRispostaCorrettaAdapter(this, R.layout.list_element_domanda_risposta_corretta, risposteSbagliate);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void onClickHomepage(View view){
        Intent i= new Intent(this, Homepage.class);
        startActivity(i);
    }

    public void onClickListaPreferiti(View v){
        Intent i= new Intent(this, ListaPreferiti.class);
        startActivity(i);
    }

    public void onClickProfilo(View v){
        Intent i= new Intent(this, ProfiloPersonale.class);
        startActivity(i);
    }

}
