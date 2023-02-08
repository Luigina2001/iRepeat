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

import com.example.irepeat.Bean.QuizBean;
import com.example.irepeat.Bean.RispostaBean;
import com.example.irepeat.R;

import java.util.List;

public class OpzioniRispostaAdapter extends ArrayAdapter<RispostaBean> {

    private LayoutInflater inflater;

    public OpzioniRispostaAdapter(@NonNull Context context, int resource, @NonNull List<RispostaBean> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        int orientation = 0;

        if (view == null) {

            if (orientation == Configuration.ORIENTATION_LANDSCAPE)
                view = inflater.inflate(R.layout.list_element_opzioni_risposta_landscape, null);
            else
                view = inflater.inflate(R.layout.list_element_opzioni_risposta, null);
        }

        RispostaBean r = getItem(position);

        TextView risposta = view.findViewById(R.id.opzioneRisposta);
        risposta.setText(r.getTesto());

        return view;
    }
}
