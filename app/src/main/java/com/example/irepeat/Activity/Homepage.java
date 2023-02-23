package com.example.irepeat.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.irepeat.R;
import com.example.irepeat.Utils.MyPreferences;

public class Homepage extends AppCompatActivity {

    private MyPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }


        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.homepage_landscape);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.homepage);
        }

        preferences= new MyPreferences(this);
    }

    public void onClickCreaNuovoQuiz(View v){

        if(preferences.isLoggedIn()){
            Intent i = new Intent(this, CreazioneQuiz.class);
            startActivity(i);
        }
        else {
            Toast t= Toast.makeText(this, "Effettua il login per creare un nuovo quiz", Toast.LENGTH_LONG);
            t.show();
        }

    }

    public void onClickRicerca(View v){
        Intent i= new Intent(this, Ricerca.class);
        startActivity(i);
    }

    public void onClickListaPreferiti(View v){

        if(preferences.isLoggedIn()){
            Intent i= new Intent(this, ListaPreferiti.class);
            startActivity(i);
        }
        else {
            Toast t= Toast.makeText(this, "Effettua il login per visualizzare i preferiti", Toast.LENGTH_LONG);
            t.show();
        }
    }

    public void onClickProfilo(View v){
        // se l'utente è loggato
        MyPreferences preferences= new MyPreferences(this);
        if(preferences.isLoggedIn()) {
            Intent i= new Intent(this, ProfiloPersonale.class);
            startActivity(i);
        }
        //altrimenti
        else {
            Intent i = new Intent(this, Login.class);
            startActivity(i);
        }
    }

    public void onClickChatbot(View v){
        Intent i= new Intent(this, ActivityChatbot.class);
        startActivity(i);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
