package com.example.irepeat.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.irepeat.Bean.DomandaBean;
import com.example.irepeat.Bean.QuizBean;
import com.example.irepeat.Bean.RispostaBean;
import com.example.irepeat.Bean.UtenteBean;
import com.example.irepeat.DAO.DBHelper;
import com.example.irepeat.DAO.DomandaDAO;
import com.example.irepeat.DAO.QuizDAO;
import com.example.irepeat.DAO.RispostaDAO;
import com.example.irepeat.DAO.UtenteDAO;
import com.example.irepeat.R;
import com.example.irepeat.Utils.MyPreferences;
import com.example.irepeat.Utils.ParcelableRelativeLayout;

import java.util.ArrayList;

public class ModificaQuiz extends AppCompatActivity {

    private LinearLayout listViewCreazioneDomande;
    private EditText nomeQuiz, descrizioneQuiz, disciplinaQuiz;
    private RadioButton pubblica, privata;
    private NumberPicker orePicker, minutiPicker;
    private ArrayList<View> domande= new ArrayList<>();
    private int count=0;
    private MyPreferences preferences;
    private boolean flag;
    private String nome, descrizione, disciplina, ore, minuti;
    private int visibilita;
    private TextView modifica;
    private ArrayList<DomandaBean> domandeQuiz = new ArrayList<>();
    private int counter = 1;
    private int idQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.creazione_quiz_landscape);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.creazione_quiz);
        }

        modifica = findViewById(R.id.textViewCreaQuiz);
        modifica.setText("Modifica il tuo quiz");

        listViewCreazioneDomande= findViewById(R.id.listViewCreazioneDomande);

        if (savedInstanceState!=null){
            nome=savedInstanceState.getString("nome");
            descrizione=savedInstanceState.getString("descrizione");
            disciplina= savedInstanceState.getString("disciplina");
            ore= savedInstanceState.getString("ore");
            minuti= savedInstanceState.getString("minuti");
            visibilita= savedInstanceState.getInt("visibilita");

            nomeQuiz= findViewById(R.id.nomeQuiz);
            nomeQuiz.setText(nome);

            descrizioneQuiz= findViewById(R.id.descrizioneQuiz);
            descrizioneQuiz.setText(descrizione);

            disciplinaQuiz= findViewById(R.id.disciplinaQuiz);
            disciplinaQuiz.setText(disciplina);

            pubblica= findViewById(R.id.visibilitaQuizPublic);
            privata= findViewById(R.id.visibilitaQuizPrivate);

            if (visibilita==1){
                pubblica.setChecked(true);
                privata.setChecked(false);
            }
            else if (visibilita==0){
                pubblica.setChecked(false);
                privata.setChecked(true);
            }

            orePicker= findViewById(R.id.ore);
            ore=savedInstanceState.getString("ore");
            orePicker.setMinValue(0);
            orePicker.setMaxValue(5);
            orePicker.setValue(Integer.parseInt(ore));

            minutiPicker= findViewById(R.id.minuti);
            minuti=savedInstanceState.getString("minuti");
            minutiPicker.setMinValue(0);
            minutiPicker.setMaxValue(59);
            minutiPicker.setValue(Integer.parseInt(minuti));
            count= savedInstanceState.getInt("count");
            counter= savedInstanceState.getInt("counter");

            idQuiz= savedInstanceState.getInt("idQuiz");

            ParcelableRelativeLayout[] parcelableRelativeLayouts = (ParcelableRelativeLayout[]) savedInstanceState.getSerializable("domande");
            for (ParcelableRelativeLayout parcelableRelativeLayout : parcelableRelativeLayouts) {
                domande.add(parcelableRelativeLayout.getView());
            }

            if (domande.size()>0){
                for (View v: domande) {
                    ViewGroup parent = (ViewGroup) v.getParent();
                    if (parent != null) {
                        parent.removeView(v);
                    }
                    listViewCreazioneDomande.addView(v);
                }
            }

        }else {

            Intent i = getIntent();
            idQuiz = i.getIntExtra("id",-1);

            if(idQuiz >0){
                QuizDAO dao= new QuizDAO(new DBHelper(this));
                dao.open();
                QuizBean quiz = dao.select(idQuiz);
                dao.close();

                nomeQuiz= findViewById(R.id.nomeQuiz);
                nomeQuiz.setText(quiz.getNome());
                descrizioneQuiz= findViewById(R.id.descrizioneQuiz);
                descrizioneQuiz.setText(quiz.getDescrizione());
                disciplinaQuiz= findViewById(R.id.disciplinaQuiz);
                disciplinaQuiz.setText(quiz.getDisciplina());

                orePicker = findViewById(R.id.ore);
                orePicker.setMinValue(0);
                orePicker.setMaxValue(5);

                minutiPicker = findViewById(R.id.minuti);
                minutiPicker.setMinValue(0);
                minutiPicker.setMaxValue(59);

                String tempo= quiz.getDurata();
                Log.d("DebugTempo", tempo);
                String[] temp= tempo.split(":");
                ore= temp[0];
                minuti= temp[1];
                orePicker.setValue(Integer.parseInt(ore));
                minutiPicker.setValue(Integer.parseInt(minuti));

                visibilita = quiz.getVisibilita();
                pubblica= findViewById(R.id.visibilitaQuizPublic);
                privata= findViewById(R.id.visibilitaQuizPrivate);

                if (visibilita==1){
                    pubblica.setChecked(true);
                    privata.setChecked(false);
                }
                else if (visibilita==0){
                    pubblica.setChecked(false);
                    privata.setChecked(true);
                }

                quiz.setDomande(new DBHelper(this));
                domandeQuiz =  quiz.getDomande();

                ImageView aggiungi = findViewById(R.id.aggiungiDomandaButton);

                if (domandeQuiz.size()>0) {
                    for (DomandaBean d : domandeQuiz) {
                        d.setRisposte(new DBHelper(this));
                        if (d.getRisposte().size()>0)
                            onClickAggiungiDomandaRisposta(aggiungi, d);
                    }
                }


            }

        }

        preferences= new MyPreferences(this);
    }


    public void onClickBack(View view){
        super.onBackPressed();
    }

    public void onClickCreaQuiz(View view){

        flag=true;

        nomeQuiz= findViewById(R.id.nomeQuiz);
        nome= nomeQuiz.getText().toString();

        descrizioneQuiz= findViewById(R.id.descrizioneQuiz);
        descrizione= descrizioneQuiz.getText().toString();

        disciplinaQuiz= findViewById(R.id.disciplinaQuiz);
        disciplina= disciplinaQuiz.getText().toString();

        pubblica= findViewById(R.id.visibilitaQuizPublic);
        privata= findViewById(R.id.visibilitaQuizPrivate);
        if (pubblica.isChecked()){
            visibilita=1;
        }
        else if (privata.isChecked()){
            visibilita=0;
        }
        else{
            visibilita=-1;
        }

        String tempo="";
        orePicker= findViewById(R.id.ore);
        ore= String.valueOf(orePicker.getValue());
        minutiPicker= findViewById(R.id.minuti);
        minuti= String.valueOf(minutiPicker.getValue());
        tempo=ore+":"+minuti;

        ArrayList<DomandaBean> listDomande= new ArrayList<>();

        if (nome.equals("") || descrizione.equals("") || disciplina.equals("") || visibilita<0 || tempo.equals("")){
            flag=false;
            Toast.makeText(getApplicationContext(), "Riempire tutti i campi richiesti", Toast.LENGTH_LONG).show();
        }
        else if (domande.size()<=0){
            Log.d("MYDEBUG", "nome: "+nome+" descrizione: "+descrizione+" visibilita: "+visibilita+ " tempo: "+tempo);
            Toast.makeText(getApplicationContext(), "Inserire almeno una domanda", Toast.LENGTH_LONG).show();
            flag=false;
        }
        else{
            int id= Integer.parseInt(preferences.getId());
            UtenteBean utente;
            UtenteDAO dao= new UtenteDAO(new DBHelper(this));
            dao.open();
            utente=dao.doRetrieveById(id);
            dao.close();
            QuizBean quiz= new QuizBean(descrizione, nome, disciplina, 0, tempo, visibilita, utente);
            quiz.setId(idQuiz);
            QuizDAO quizDAO= new QuizDAO(new DBHelper(this));
            quizDAO.open();
            if (quizDAO.update(quiz)) {
                for (View v : domande) {
                    String testo = ((EditText) (v.findViewById(R.id.testoDomanda))).getText().toString();
                    if (testo.equals("")) {
                        flag=false;
                        Toast.makeText(getApplicationContext(), "Inserire il testo della domanda", Toast.LENGTH_LONG).show();
                    } else {
                        DomandaBean domandaBean = new DomandaBean(testo, quiz);
                        EditText testoDomandaET= (EditText)(v.findViewById(R.id.testoDomanda));
                        if (testoDomandaET.getTag()!=null)
                            domandaBean.setId((int)testoDomandaET.getTag());
                        else
                            domandaBean.setId(-1);

                        String temp1 = ((EditText) (v.findViewById(R.id.risposta1))).getText().toString();
                        String temp2 = ((EditText) (v.findViewById(R.id.risposta2))).getText().toString();
                        String temp3 = ((EditText) (v.findViewById(R.id.risposta3))).getText().toString();
                        String temp4 = ((EditText) (v.findViewById(R.id.risposta4))).getText().toString();
                        String temp5 = ((EditText) (v.findViewById(R.id.risposta5))).getText().toString();

                        if (temp1.equals("") && temp2.equals("") && temp3.equals("") && temp4.equals("") && temp5.equals("")) {
                            Toast.makeText(getApplicationContext(), "Inserire almeno un'opzione di risposta", Toast.LENGTH_LONG).show();
                            flag=false;
                        }
                        else {
                            ArrayList<RispostaBean> listRisposte= new ArrayList<>();
                            if (!(temp1.equals(""))) {
                                EditText rispostaET= (EditText) (v.findViewById(R.id.risposta1));
                                RispostaBean r = new RispostaBean();
                                r.setTesto(temp1);
                                r.setCorretta(0);
                                if (rispostaET.getTag()!=null)
                                    r.setId((int)rispostaET.getTag());
                                else
                                    r.setId(-1);
                                listRisposte.add(r);
                            }
                            if (!(temp2.equals(""))) {
                                EditText rispostaET= (EditText) (v.findViewById(R.id.risposta2));
                                RispostaBean r = new RispostaBean();
                                r.setTesto(temp2);
                                r.setCorretta(0);
                                if (rispostaET.getTag()!=null)
                                    r.setId((int)rispostaET.getTag());
                                else
                                    r.setId(-1);
                                listRisposte.add(r);
                            }
                            if (!(temp3.equals(""))) {
                                EditText rispostaET= (EditText) (v.findViewById(R.id.risposta3));
                                RispostaBean r = new RispostaBean();
                                r.setTesto(temp3);
                                r.setCorretta(0);
                                if (rispostaET.getTag()!=null)
                                    r.setId((int)rispostaET.getTag());
                                else
                                    r.setId(-1);
                                listRisposte.add(r);
                            }
                            if (!(temp4.equals(""))) {
                                EditText rispostaET= (EditText) (v.findViewById(R.id.risposta4));
                                RispostaBean r = new RispostaBean();
                                r.setTesto(temp4);
                                r.setCorretta(0);
                                if (rispostaET.getTag()!=null)
                                    r.setId((int)rispostaET.getTag());
                                else
                                    r.setId(-1);
                                listRisposte.add(r);
                            }
                            if (!(temp5.equals(""))) {
                                EditText rispostaET= (EditText) (v.findViewById(R.id.risposta5));
                                RispostaBean r = new RispostaBean();
                                r.setTesto(temp5);
                                r.setCorretta(0);
                                if (rispostaET.getTag()!=null)
                                    r.setId((int)rispostaET.getTag());
                                else
                                    r.setId(-1);
                                listRisposte.add(r);
                            }

                            String rispostaCorretta = ((EditText) (v.findViewById(R.id.rispostaCorretta))).getText().toString();
                            if (rispostaCorretta.equals("")) {
                                Toast.makeText(getApplicationContext(), "Inserire una risposta corretta", Toast.LENGTH_LONG).show();
                                flag=false;
                            } else {
                                EditText rispostaET= (EditText) (v.findViewById(R.id.rispostaCorretta));
                                RispostaBean r = new RispostaBean();
                                r.setTesto(rispostaCorretta);
                                r.setCorretta(1);
                                if (rispostaET.getTag()!=null)
                                    r.setId((int)rispostaET.getTag());
                                else
                                    r.setId(-1);
                                listRisposte.add(r);
                                DomandaDAO domandaDAO = new DomandaDAO(new DBHelper(this));
                                domandaDAO.open();
                                if (!(domandaDAO.update(domandaBean))) {
                                    Toast.makeText(getApplicationContext(), "Aggiornamento domanda nel DB non riuscita", Toast.LENGTH_LONG).show();
                                    flag=false;
                                } else {
                                    domandaDAO.close();
                                    RispostaDAO rispostaDAO = new RispostaDAO(new DBHelper(this));
                                    rispostaDAO.open();
                                    for (RispostaBean rispostaBean : listRisposte) {
                                        rispostaBean.setDomanda(domandaBean);
                                        if (!(rispostaDAO.update(rispostaBean))) {
                                            Toast.makeText(getApplicationContext(), "Aggiornamento risposta nel DB non riuscita", Toast.LENGTH_LONG).show();
                                            flag=false;
                                        }
                                    }
                                    domandaBean.setRisposte(listRisposte);
                                    listDomande.add(domandaBean);
                                }
                            }
                        }
                    }
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "Inserimento quiz nel DB non riuscito", Toast.LENGTH_LONG).show();
                flag=false;
            }
            quiz.setDomande(listDomande);
            Log.d("MYDEBUG", quiz.toString());
            if (!flag){
                quizDAO.close();
            }
            else {
                quizDAO.close();
                Intent i = new Intent(this, ProfiloPersonale.class);
                startActivity(i);
            }
        }

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
                    ViewGroup.LayoutParams params = risposta2.getLayoutParams();
                    params.height = 200; // l'altezza viene impostata a 160 pixel
                    risposta2.setLayoutParams(params);
                }
                else if (risposta3.getVisibility()==View.INVISIBLE){
                    risposta3.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams params = risposta3.getLayoutParams();
                    params.height = 200;
                    risposta3.setLayoutParams(params);
                }
                else if (risposta4.getVisibility()==View.INVISIBLE){
                    risposta4.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams params = risposta4.getLayoutParams();
                    params.height = 200;
                    risposta4.setLayoutParams(params);
                }
                else if (risposta5.getVisibility()==View.INVISIBLE){
                    risposta5.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams params = risposta5.getLayoutParams();
                    params.height = 200;
                    risposta5.setLayoutParams(params);
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "È possibile aggiungere massimo 5 opzioni di risposta errata", Toast.LENGTH_LONG).show();
                }
            }
        });
        TextView idDomanda= domandaView.findViewById(R.id.idDomanda);
        count++;
        idDomanda.setText("Domanda n."+count);
        domande.add(domandaView);

        for (View v: domande) {
            ViewGroup parent = (ViewGroup) v.getParent();
            if (parent != null) {
                parent.removeView(v);
            }
            listViewCreazioneDomande.addView(v);
        }
    }

    public void onClickAggiungiDomandaRisposta(View view, DomandaBean d){

        View temp= null;
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            temp= LayoutInflater.from(this).inflate(R.layout.list_element_domanda_creazione_quiz_landscape, listViewCreazioneDomande, false);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            temp= LayoutInflater.from(this).inflate(R.layout.list_element_domanda_creazione_quiz, listViewCreazioneDomande, false);
        }

        View domandaView= temp;
        counter=1;

        EditText testoDomanda = domandaView.findViewById(R.id.testoDomanda);

        testoDomanda.setText(d.getTesto());
        testoDomanda.setTag(d.getId());

        ArrayList<RispostaBean> risposte = d.getRisposte();
        ImageView aggiungiRisposta= domandaView.findViewById(R.id.aggiungiRispostaButton);


        for (RispostaBean r:risposte){
            if(r.getCorretta() == 1){
                EditText rispostaCorretta = domandaView.findViewById(R.id.rispostaCorretta);
                rispostaCorretta.setText(r.getTesto());
                rispostaCorretta.setTag(r.getId());
            }else{
                String rispostaId = "risposta";
                rispostaId = rispostaId + counter;
                EditText risposta = domandaView.findViewById(getResources().getIdentifier(rispostaId,"id",getPackageName()));
                risposta.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = risposta.getLayoutParams();
                params.height = 200;
                risposta.setLayoutParams(params);
                risposta.setText(r.getTesto());
                risposta.setTag(r.getId());
                counter++;
            }

        }

        aggiungiRisposta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText risposta2= domandaView.findViewById(R.id.risposta2);
                EditText risposta3= domandaView.findViewById(R.id.risposta3);
                EditText risposta4= domandaView.findViewById(R.id.risposta4);
                EditText risposta5= domandaView.findViewById(R.id.risposta5);

                if (risposta2.getVisibility()==View.INVISIBLE){
                    risposta2.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams params = risposta2.getLayoutParams();
                    params.height = 200; // l'altezza viene impostata a 160 pixel
                    risposta2.setLayoutParams(params);
                }
                else if (risposta3.getVisibility()==View.INVISIBLE){
                    risposta3.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams params = risposta3.getLayoutParams();
                    params.height = 200;
                    risposta3.setLayoutParams(params);
                }
                else if (risposta4.getVisibility()==View.INVISIBLE){
                    risposta4.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams params = risposta4.getLayoutParams();
                    params.height = 200;
                    risposta4.setLayoutParams(params);
                }
                else if (risposta5.getVisibility()==View.INVISIBLE){
                    risposta5.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams params = risposta5.getLayoutParams();
                    params.height = 200;
                    risposta5.setLayoutParams(params);
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "È possibile aggiungere massimo 5 opzioni di risposta errata", Toast.LENGTH_LONG).show();
                }
            }
        });

        TextView idDomanda= domandaView.findViewById(R.id.idDomanda);
        count++;
        idDomanda.setText("Domanda n."+count);

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

        nomeQuiz= findViewById(R.id.nomeQuiz);
        nome= nomeQuiz.getText().toString();

        descrizioneQuiz= findViewById(R.id.descrizioneQuiz);
        descrizione= descrizioneQuiz.getText().toString();

        disciplinaQuiz= findViewById(R.id.disciplinaQuiz);
        disciplina= disciplinaQuiz.getText().toString();

        pubblica= findViewById(R.id.visibilitaQuizPublic);
        privata= findViewById(R.id.visibilitaQuizPrivate);
        if (pubblica.isChecked()){
            visibilita=1;
        }
        else if (privata.isChecked()){
            visibilita=0;
        }
        else{
            visibilita=-1;
        }

        orePicker= findViewById(R.id.ore);
        ore= String.valueOf(orePicker.getValue());
        minutiPicker= findViewById(R.id.minuti);
        minuti= String.valueOf(minutiPicker.getValue());
        outState.putString("nome", nome);
        outState.putString("descrizione", descrizione);
        outState.putString("disciplina", disciplina);
        outState.putString("ore", ore);
        outState.putString("minuti", minuti);
        outState.putInt("visibilita", visibilita);

        outState.putInt("count", count);
        outState.putInt("counter", counter);
        outState.putInt("idQuiz", idQuiz);

        ParcelableRelativeLayout[] parcelableRelativeLayouts = new ParcelableRelativeLayout[domande.size()];
        for (int i = 0; i < domande.size(); i++) {
            parcelableRelativeLayouts[i] = new ParcelableRelativeLayout( domande.get(i));
        }

        // Salvataggio dell'array di oggetti ParcelableRelativeLayout nel Bundle
        outState.putSerializable("domande", parcelableRelativeLayouts);

        super.onSaveInstanceState(outState);
    }
}
