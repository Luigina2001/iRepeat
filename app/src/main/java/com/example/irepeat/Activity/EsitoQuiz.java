package com.example.irepeat.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.irepeat.Bean.DomandaBean;
import com.example.irepeat.Bean.RispostaBean;
import com.example.irepeat.R;

import java.util.ArrayList;
import java.util.HashMap;

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
