package com.example.irepeat.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.irepeat.Bean.DomandaBean;
import com.example.irepeat.Bean.QuizBean;
import com.example.irepeat.Bean.RispostaBean;

import java.util.ArrayList;

public class RispostaDAO {

    public RispostaDAO(DBHelper dbHelper){
        this.dbHelper =dbHelper;
    }

    public void open(){
        database= dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }


    public RispostaBean select(int id){

        // Specifichiamo le colonne che ci interessano
        String[] projection = {
                COLUMN_ID,
                COLUMN_TESTO,
                COLUMN_ID_DOMANDA,
                COLUMN_CORRETTA,
        };


        // Definiamo la parte 'where' della query.
        // es. selection="ID = ? "
        String selection;
        selection = COLUMN_ID + " = ? ";


        // Specifichiamo gli argomenti per i segnaposto (ovvero i ? nella stringa selection)
        String[] selectionArgs = {String.valueOf(id)};

        // Specifichiamo come le vogliamo ordinare le righe
        String sortOrder = null;

        // Eseguiamo la query: es. SELECT <nomi colonne> FROM <nome tavola> WHERE ...
        Cursor cursor = database.query(
                TABLE_NAME,                 // The table to query
                projection,                 // The columns to return
                selection,                  // The columns for the WHERE clause
                selectionArgs,              // The values for the WHERE clause
                null,                       // don't group the rows
                null,                       // don't filter by row groups
                sortOrder                   // The sort order
        );

        if (cursor!=null){
            cursor.moveToFirst();

            RispostaBean risposta = new RispostaBean();
            risposta.setId(cursor.getInt(0));
            risposta.setTesto(cursor.getString(1));
            risposta.setCorretta(cursor.getInt(3));

            DomandaBean domanda = new DomandaBean();
            DomandaDAO domandaDAO = new DomandaDAO(dbHelper);
            domandaDAO.open();
            domanda = domandaDAO.select(cursor.getInt(2));
            if (domanda!=null){
                risposta.setDomanda(domanda);
                return risposta;
            }
            return null;
        }
        return null;
    }

    public ArrayList<RispostaBean> selectByDomanda(DomandaBean domanda){

        ArrayList<RispostaBean> risposteDomanda= new ArrayList<>();

        // Specifichiamo le colonne che ci interessano
        String[] projection = {
                COLUMN_ID,
                COLUMN_TESTO,
                COLUMN_ID_DOMANDA,
                COLUMN_CORRETTA,
        };


        // Definiamo la parte 'where' della query.
        // es. selection="ID = ? "
        String selection;
        selection = COLUMN_ID_DOMANDA + " = ? ";


        // Specifichiamo gli argomenti per i segnaposto (ovvero i ? nella stringa selection)
        String[] selectionArgs = {String.valueOf(domanda.getId())};

        // Specifichiamo come le vogliamo ordinare le righe
        String sortOrder = null;

        // Eseguiamo la query: es. SELECT <nomi colonne> FROM <nome tavola> WHERE ...
        Cursor cursor = database.query(
                TABLE_NAME,                 // The table to query
                projection,                 // The columns to return
                selection,                  // The columns for the WHERE clause
                selectionArgs,              // The values for the WHERE clause
                null,                       // don't group the rows
                null,                       // don't filter by row groups
                sortOrder                   // The sort order
        );

        if (cursor!=null){

            cursor.moveToFirst();

            do {
                RispostaBean risposta = new RispostaBean();
                risposta.setId(cursor.getInt(0));
                risposta.setTesto(cursor.getString(1));
                risposta.setCorretta(cursor.getInt(3));

                DomandaBean domanda1 = new DomandaBean();
                DomandaDAO domandaDAO = new DomandaDAO(dbHelper);
                domandaDAO.open();
                domanda1 = domandaDAO.select(cursor.getInt(2));
                if (domanda1 != null) {
                    risposta.setDomanda(domanda1);
                    risposteDomanda.add(risposta);
                }
                else
                    return null;
            }while (cursor.moveToNext());
        }
        return risposteDomanda;
    }


    public boolean insert(RispostaBean risposta){

        ContentValues values= new ContentValues();
        int id;

        if (risposta.getTesto() != null && risposta.getDomanda() != null) {

            if ((id=doRetrieveMaxId())>=0) {
                id++;
                risposta.setId(id);
            }
            else
                return false;
            values.put(COLUMN_ID, id);
            values.put(COLUMN_TESTO, risposta.getTesto());
            values.put(COLUMN_ID_DOMANDA, risposta.getDomanda().getId());

            long check= database.insert(TABLE_NAME, null, values);

            if (check>0)
                return true;
            return false;
        }

        return false;

    }

    public int doRetrieveMaxId (){

        Cursor cursor= database.rawQuery("SELECT MAX("+COLUMN_ID+") FROM "+TABLE_NAME+";", null);

        if (cursor!=null){
            cursor.moveToFirst();
            if (cursor.getCount()>0)
                return cursor.getInt(0);
            else
                return 0;
        }

        return -1;

    }


    private static final String TABLE_NAME = "Risposta";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TESTO = "testo";
    private static final String COLUMN_ID_DOMANDA = "domanda";
    private static final String COLUMN_CORRETTA = "corretta";


    private DBHelper dbHelper;
    private SQLiteDatabase database;

}
