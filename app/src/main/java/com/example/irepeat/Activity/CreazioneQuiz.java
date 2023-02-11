package com.example.irepeat.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.irepeat.Adapter.DomandaCreazioneQuizAdapter;
import com.example.irepeat.R;

import java.util.ArrayList;

public class CreazioneQuiz extends AppCompatActivity {

    private LinearLayout listViewCreazioneDomande;
    private EditText nomeQuiz, descrizioneQuiz, disciplinaQuiz;
    private RadioButton pubblica, privata;
    private NumberPicker orePicker, minutiPicker;
    private ArrayList<View> domande= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.creazione_quiz_landscape);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.creazione_quiz);
        }

        orePicker= findViewById(R.id.ore);
        orePicker.setMinValue(0);
        orePicker.setMaxValue(5);

        minutiPicker= findViewById(R.id.minuti);
        minutiPicker.setMaxValue(59);
        minutiPicker.setMinValue(0);

        listViewCreazioneDomande= findViewById(R.id.listViewCreazioneDomande);

    }

    public void onClickBack(View view){
        super.onBackPressed();
    }

    public void onClickCreaQuiz(View view){

        nomeQuiz= findViewById(R.id.nomeQuiz);
        String nome= nomeQuiz.getText().toString();

        descrizioneQuiz= findViewById(R.id.descrizioneQuiz);
        String descrizione= descrizioneQuiz.getText().toString();

        disciplinaQuiz= findViewById(R.id.disciplinaQuiz);
        String disciplina= disciplinaQuiz.getText().toString();

        String visibilita;
        pubblica= findViewById(R.id.visibilitaQuizPublic);
        privata= findViewById(R.id.visibilitaQuizPrivate);
        if (pubblica.isChecked()){
            visibilita="pubblica";
        }
        else{
            visibilita="privata";
        }

        String tempo="";
        String ore, minuti;
        orePicker= findViewById(R.id.ore);
        ore= String.valueOf(orePicker.getValue());
        minutiPicker= findViewById(R.id.minuti);
        minuti= String.valueOf(minutiPicker.getValue());
        tempo=ore+":"+minuti;
    }

    public void onClickAggiungiDomanda(View view){

        View temp= null;
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            temp= LayoutInflater.from(this).inflate(R.layout.list_element_domanda_creazione_quiz_landscape, listViewCreazioneDomande, false);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            temp= LayoutInflater.from(this).inflate(R.layout.list_element_domanda_creazione_quiz, listViewCreazioneDomande, false);
        }

        View domandaView= temp;

        ImageView aggiungiRisposta= domandaView.findViewById(R.id.aggiungiRispostaButton);
        aggiungiRisposta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText risposta2= domandaView.findViewById(R.id.risposta2);
                EditText risposta3= domandaView.findViewById(R.id.risposta3);
                EditText risposta4= domandaView.findViewById(R.id.risposta4);
                EditText risposta5= domandaView.findViewById(R.id.risposta5);

                if (risposta2.getVisibility()==View.INVISIBLE){
                    risposta2.setVisibility(View.VISIBLE);
                }
                else if (risposta3.getVisibility()==View.INVISIBLE){
                    risposta3.setVisibility(View.VISIBLE);
                }
                else if (risposta4.getVisibility()==View.INVISIBLE){
                    risposta4.setVisibility(View.VISIBLE);
                }
                else if (risposta5.getVisibility()==View.INVISIBLE){
                    risposta5.setVisibility(View.VISIBLE);
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "È possibile aggiungere massimo 5 opzioni di risposta errata", Toast.LENGTH_LONG).show();
                }
            }
        });
        domande.add(domandaView);

        for (View v: domande) {
            ViewGroup parent = (ViewGroup) v.getParent();
            if (parent != null) {
                parent.removeView(v);
            }
            listViewCreazioneDomande.addView(v);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
