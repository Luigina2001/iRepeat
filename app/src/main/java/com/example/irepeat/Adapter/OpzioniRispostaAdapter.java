package com.example.irepeat.Adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.irepeat.Activity.IniziaQuiz;
import com.example.irepeat.Bean.QuizBean;
import com.example.irepeat.Bean.RispostaBean;
import com.example.irepeat.R;

import java.util.List;

public class OpzioniRispostaAdapter extends ArrayAdapter<RispostaBean> {

    private LayoutInflater inflater;
    private Context context;
    private int selectedPosition=-1;
    private IniziaQuiz iniziaQuiz;

    public OpzioniRispostaAdapter(@NonNull Context context, int resource, @NonNull List<RispostaBean> objects, IniziaQuiz
            iniziaQuizActivity) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        this.context=context;
        this.iniziaQuiz= iniziaQuizActivity;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        int orientation = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            orientation = context.getResources().getConfiguration().orientation;
        }

        if (view == null) {

            if (orientation == Configuration.ORIENTATION_LANDSCAPE)
                view = inflater.inflate(R.layout.list_element_opzioni_risposta_landscape, null);
            else
                view = inflater.inflate(R.layout.list_element_opzioni_risposta, null);
        }

        RispostaBean r = getItem(position);

        RadioButton risposta = view.findViewById(R.id.opzioneRisposta);
        risposta.setText(r.getTesto());
        risposta.setChecked(position == selectedPosition);
        risposta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                notifyDataSetChanged();
                iniziaQuiz.salvaRisposta(position);
            }
        });

        return view;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        notifyDataSetChanged();
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }


}
