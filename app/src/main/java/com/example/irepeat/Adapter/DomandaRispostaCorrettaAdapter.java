package com.example.irepeat.Adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.irepeat.Bean.DomandaBean;
import com.example.irepeat.Bean.RispostaBean;
import com.example.irepeat.R;

import java.util.ArrayList;
import java.util.List;

public class DomandaRispostaCorrettaAdapter extends ArrayAdapter<DomandaBean> {

    private LayoutInflater inflater;
    private Context context;

    public DomandaRispostaCorrettaAdapter(@NonNull Context context, int resource, @NonNull List<DomandaBean> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        int orientation = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            orientation = context.getResources().getConfiguration().orientation;
        }

        if (view == null) {

            if (orientation == Configuration.ORIENTATION_LANDSCAPE)
                view = inflater.inflate(R.layout.list_element_domanda_risposta_corretta_landscape, null);
            else
                view = inflater.inflate(R.layout.list_element_domanda_risposta_corretta, null);
        }

        DomandaBean d = getItem(position);

        TextView domanda = view.findViewById(R.id.testoDomanda);
        TextView rispostaCorretta = view.findViewById(R.id.testoRispostaCorretta);

        domanda.setText(d.getTesto());

        ArrayList<RispostaBean> risposte = d.getRisposte();

        for (RispostaBean r:risposte) {

            if(r.getCorretta() != 0)
                rispostaCorretta.setText(r.getTesto());
        }

        domanda.setTag(position);
        rispostaCorretta.setTag(position);

        return view;
    }
}
