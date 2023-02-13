package com.example.irepeat.Adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.irepeat.Bean.QuizBean;
import com.example.irepeat.Bean.RispostaBean;
import com.example.irepeat.R;

import java.util.List;

public class RicercaQuizAdapter extends ArrayAdapter<QuizBean> {

    private LayoutInflater inflater;
    private Context context;

    public RicercaQuizAdapter(@NonNull Context context, int resource, @NonNull List<QuizBean> objects) {
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
                view = inflater.inflate(R.layout.list_element_ricerca_quiz_landscape, null);
            else
                view = inflater.inflate(R.layout.list_element_ricerca_quiz, null);
        }

        QuizBean q = getItem(position);

        TextView nomeQuiz = view.findViewById(R.id.titoloQuiz);
        TextView disciplina = view.findViewById(R.id.disciplinaQuiz);
        TextView creator = view.findViewById(R.id.creatorQuiz);
        nomeQuiz.setTag(q.getId());

        nomeQuiz.setText(q.getNome());
        disciplina.setText(q.getDisciplina());
        creator.setText(q.getUtente().getNickname());

        ImageView cuore = view.findViewById(R.id.cuoreButton);
        cuore.setTag(q.getId());
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
            }
        });

        if(q.getPreferito() == 0) {
            cuore.setImageDrawable(context.getResources().getDrawable(R.drawable.cuore_vuoto));
            Log.d("MYDEBUG", "quiz NON preferito");
        }
        else {
            cuore.setImageDrawable(context.getResources().getDrawable(R.drawable.cuore_pieno));
            Log.d("MYDEBUG", "quiz preferito");
        }

        return view;
    }
}
