package com.example.irepeat.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.irepeat.R;

public class IniziaQuiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.inizia_quiz_landscape);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.inizia_quiz);
        }

        Intent i=getIntent();
        Log.d("MYDEBUG", "id quiz= "+i.getIntExtra("id", -1));


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
