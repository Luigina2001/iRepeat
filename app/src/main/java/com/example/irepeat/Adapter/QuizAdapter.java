package com.example.irepeat.Adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.irepeat.Bean.DomandaBean;
import com.example.irepeat.Bean.QuizBean;
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
        nomeQuiz.setTag(q.getId());

        nomeQuiz.setText(q.getNome());
        disciplina.setText(q.getDisciplina());

        numDomande.setText("Numero domande: "+ q.getDomande().size());

        ImageView cuore = view.findViewById(R.id.cuoreButton);
        cuore.setTag(q.getId());

        if(q.getPreferito() == 0)
            cuore.setImageDrawable(view.getResources().getDrawable(R.drawable.cuore_vuoto));
        else
            cuore.setImageDrawable(view.getResources().getDrawable(R.drawable.cuore_pieno));

        return view;
    }
}
