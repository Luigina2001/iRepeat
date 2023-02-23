package com.example.irepeat.Activity;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.irepeat.Adapter.MessageListAdapter;
import com.example.irepeat.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ActivityChatbot extends AppCompatActivity {

    private ListView listView;
    private View btnSend;
    private EditText messaggioDigitato;
    boolean isMine =true;
    private ArrayList<Messaggio> messaggi= new ArrayList<>();
    private ArrayAdapter<Messaggio> adapter;
    private String utterances;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_chatbot_landscape);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_chatbot);
        }

        if (savedInstanceState!=null){
            messaggi= (ArrayList<Messaggio>) savedInstanceState.getSerializable("messaggi");
        }
        Log.d("--debug--", "Lancio ActivityChatbot");

        listView =(ListView)findViewById(R.id.messaggiList);
        btnSend =findViewById(R.id.sendButton);
        messaggioDigitato =(EditText)findViewById(R.id.editTest_message);



        adapter = new MessageListAdapter(this, R.layout.activity_messaggio_bot, messaggi);
        listView.setAdapter(adapter);


        //inizializzazione giorno chat
        GregorianCalendar g= new GregorianCalendar();
        int numGiorno= g.get(Calendar.DAY_OF_MONTH);
        int numGiornoSettimana= g.get(Calendar.DAY_OF_WEEK);
        String giornoSettimana="";
        switch (numGiornoSettimana){
            case 1: giornoSettimana="Domenica";break;
            case 2:  giornoSettimana="Lunedi";break;
            case 3:  giornoSettimana="Martedi";break;
            case 4:  giornoSettimana="Mercoledi";break;
            case 5:  giornoSettimana="Giovedi";break;
            case 6:  giornoSettimana="Venerdi";break;
            case 7:  giornoSettimana="Sabato";break;
        }

        String giorno=giornoSettimana+" "+numGiorno;

        TextView data= findViewById(R.id.textViewData);
        data.setText(giorno);


        if (!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }

        Python py= Python.getInstance();
        //nome del nostro file python
        final PyObject pyobj= py.getModule("chatbot");


        String dateTime = DateTimeFormatter.ofPattern("hh:mm a").format(LocalDateTime.now());
        //messaggio di benvenuto chatBot
        if (savedInstanceState==null) {
            Log.d("--debug--", "Messaggio di benvenuto --> Ciao, sono il chatbot di iRepeat. Come posso aiutarti?");
            Messaggio messaggio = new Messaggio("Ciao, sono il chatbot di iRepeat. Come posso aiutarti?", "iRepeat chatbot", dateTime, isMine);
            messaggi.add(messaggio);
            adapter.notifyDataSetChanged();
            messaggioDigitato.setText("");
            if (isMine) {
                isMine = false;
            } else {
                isMine = true;
            }
        }


        btnSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(messaggioDigitato.getText().toString().trim().equals("")){
                    Toast.makeText(ActivityChatbot.this,"Nessun testo inserito..",Toast.LENGTH_SHORT).show();
                }else {

                    //messaggio iscritto
                    Log.d("--debug--", "Messaggio iscritto (utterances) --> "+messaggioDigitato.getText().toString());
                    String dateTime = DateTimeFormatter.ofPattern("hh:mm a").format(LocalDateTime.now());
                    Messaggio messaggio = new Messaggio(messaggioDigitato.getText().toString(),null,dateTime,isMine);
                    utterances= messaggioDigitato.getText().toString();
                    messaggi.add(messaggio);
                    adapter.notifyDataSetChanged();
                    messaggioDigitato.setText("");
                    if (isMine) {
                        isMine = false;
                    } else {
                        isMine = true;
                    }



                    //risposta chatBot al messaggio

                    Messaggio rispostaChatBot = new Messaggio();

                    PyObject obj= pyobj.callAttr("main", utterances); //PyObject obj= pyobj.callAttr(function name, first argument, second argument, ...);

                    String risposta;

                    //System.out.println(obj.toString());
                    if (obj==null)
                        risposta= "Non ho capito la domanda";
                    else
                        risposta=obj.toString();
                    Log.d("--debug--", "Risposta chatbot --> "+risposta);
                    dateTime = DateTimeFormatter.ofPattern("hh:mm a").format(LocalDateTime.now());
                    rispostaChatBot.setTesto(risposta);
                    rispostaChatBot.setUtente("iRepeat chatbot");
                    rispostaChatBot.setOra(dateTime);
                    rispostaChatBot.setMine(isMine);
                    //rispostaChatBot = new Messaggio(risposta,"iLike chatbot",dateTime,isMine);
                    messaggi.add(rispostaChatBot);
                    adapter.notifyDataSetChanged();
                    messaggioDigitato.setText("");
                    if (isMine) {
                        isMine = false;
                    } else {
                        isMine = true;
                    }
                }
            }
        });
    }

    public void onClickBack (View v){
        super.onBackPressed();
    }

    public void onClickHomepage (View v){
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable("messaggi", messaggi);
        super.onSaveInstanceState(outState);
    }

}