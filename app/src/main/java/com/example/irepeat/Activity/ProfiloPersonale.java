package com.example.irepeat.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.irepeat.R;

public class ProfiloPersonale extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView filtro;

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.profilo_personale_landscape);
            filtro= findViewById(R.id.filtro);

            //inizializzazione menu
            filtro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu menu = new PopupMenu(ProfiloPersonale.this, filtro);

                    menu.getMenuInflater().inflate(R.menu.menu_profilo_landscape, menu.getMenu());
                    menu.setOnMenuItemClickListener(item -> {
                        String filtroStringa = item.getTitle().toString();

                        if (filtroStringa.equalsIgnoreCase("Modifica credenziali")){
                            //aggiungere activity
                        }
                        else if (filtroStringa.equalsIgnoreCase("Crea quiz")){
                            Intent i= new Intent(ProfiloPersonale.this, CreazioneQuiz.class);
                            startActivity(i);
                        }
                        else if (filtroStringa.equalsIgnoreCase("Logout")){
                            //effettuare logout + messaggio avvenuto logout
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
            //inizializzazione menu
            filtro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu menu = new PopupMenu(ProfiloPersonale.this, filtro);

                    menu.getMenuInflater().inflate(R.menu.menu_profilo, menu.getMenu());
                    menu.setOnMenuItemClickListener(item -> {
                        String filtroStringa = item.getTitle().toString();

                        if (filtroStringa.equalsIgnoreCase("Modifica credenziali")){
                            //aggiungere activity
                        }
                        else if (filtroStringa.equalsIgnoreCase("Crea quiz")){
                            Intent i= new Intent(ProfiloPersonale.this, CreazioneQuiz.class);
                            startActivity(i);
                        }
                        else if (filtroStringa.equalsIgnoreCase("Logout")){
                            //effettuare logout + messaggio avvenuto logout
                            Intent i= new Intent(ProfiloPersonale.this, Homepage.class);
                            startActivity(i);
                        }
                        return true;
                    });
                    menu.show();
                }
            });
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
