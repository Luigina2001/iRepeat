package com.example.irepeat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.irepeat.Activity.IniziaQuiz;
import com.example.irepeat.Activity.ModificaQuiz;
import com.example.irepeat.Bean.DomandaBean;
import com.example.irepeat.Bean.QuizBean;
import com.example.irepeat.DAO.DBHelper;
import com.example.irepeat.DAO.QuizDAO;
import com.example.irepeat.R;

import java.util.List;

public class QuizAdapter extends ArrayAdapter<QuizBean> {

    private LayoutInflater inflater;
    private Context context;

    public QuizAdapter(@NonNull Context context, int resource, @NonNull List<QuizBean> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        int orientation = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            orientation = context.getResources().getConfiguration().orientation;
        }

        if (view == null) {

            if (orientation == Configuration.ORIENTATION_LANDSCAPE)
                view = inflater.inflate(R.layout.list_element_quiz_landscape, null);
            else
                view = inflater.inflate(R.layout.list_element_quiz, null);
        }

        QuizBean q = getItem(position);


        TextView nomeQuiz = view.findViewById(R.id.titoloQuiz);
        TextView disciplina = view.findViewById(R.id.disciplinaQuiz);
        TextView numDomande = view.findViewById(R.id.numDomandeQuiz);

        nomeQuiz.setText(q.getNome());
        nomeQuiz.setTag(q.getId());
        disciplina.setText(q.getDisciplina());

        if (q.getDomande()!=null)
            numDomande.setText("Numero domande: "+ q.getDomande().size());
        else
            numDomande.setText("Numero domande: 0");

        ImageView cuore = view.findViewById(R.id.cuoreButton);
        cuore.setTag(q.getId());

        ImageView modifica = view.findViewById(R.id.modificaButton);
        modifica.setTag(q.getId());

        modifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("id", (int) v.getTag());
                i.setClass(context, ModificaQuiz.class);
                context.startActivity(i);
            }
        });


        cuore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q.getPreferito()==0){
                    //aggiunta lista preferiti
                    Log.d("MYDEBUG", "quiz preferito");
                    q.setPreferito(1);

                }else {
                    //rimozione lista preferiti
                    Log.d("MYDEBUG", "quiz NON preferito");
                    q.setPreferito(0);
                }

                if(q.getPreferito() == 0) {
                    cuore.setImageDrawable(context.getResources().getDrawable(R.drawable.cuore_vuoto));
                    Log.d("MYDEBUG", "quiz NON preferito");
                }
                else {
                    cuore.setImageDrawable(context.getResources().getDrawable(R.drawable.cuore_pieno));
                    Log.d("MYDEBUG", "quiz preferito");
                }

                //gestione update nel DB
                QuizDAO dao = new QuizDAO(new DBHelper(context));
                dao.open();
                boolean ris = dao.update(q);
                dao.close();

                if(ris)
                    Toast.makeText(context, "Modifica preferiti effettuata", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(context, "Modifica preferiti NON effettuata", Toast.LENGTH_LONG).show();
            }
        });

        if(q.getPreferito() == 0)
            cuore.setImageDrawable(view.getResources().getDrawable(R.drawable.cuore_vuoto));
        else
            cuore.setImageDrawable(view.getResources().getDrawable(R.drawable.cuore_pieno));



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(context, IniziaQuiz.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("id", q.getId());
                context.startActivity(i);
            }
        });

        return view;
    }

}
