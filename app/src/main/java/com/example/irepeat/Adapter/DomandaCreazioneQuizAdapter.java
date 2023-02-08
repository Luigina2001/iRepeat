package com.example.irepeat.Adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.irepeat.Bean.DomandaBean;
import com.example.irepeat.Bean.RispostaBean;
import com.example.irepeat.R;

import java.util.List;

public class DomandaCreazioneQuizAdapter extends ArrayAdapter<DomandaBean> {

    private LayoutInflater inflater;

    public DomandaCreazioneQuizAdapter(Context context, int resource, List<DomandaBean> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        int orientation = 0;

        if (view == null) {

            if (orientation == Configuration.ORIENTATION_LANDSCAPE)
                view = inflater.inflate(R.layout.list_element_domanda_creazione_quiz_landscape, null);
            else
                view = inflater.inflate(R.layout.list_element_domanda_creazione_quiz, null);
        }

        DomandaBean d = getItem(position);

        EditText testoDomanda = view.findViewById(R.id.testoDomanda);
        testoDomanda.setTag(d.getId());

        EditText risposta1 = view.findViewById(R.id.risposta1);
        EditText risposta2 = view.findViewById(R.id.risposta2);
        EditText risposta3 = view.findViewById(R.id.risposta3);
        EditText risposta4 = view.findViewById(R.id.risposta4);
        EditText risposta5 = view.findViewById(R.id.risposta5);

        EditText rispostaCorretta = view.findViewById(R.id.rispostaCorretta);

        return view;
    }
}
