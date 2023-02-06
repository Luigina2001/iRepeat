package com.example.irepeat.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.irepeat.Bean.DomandaBean;
import com.example.irepeat.Bean.QuizBean;
import com.example.irepeat.Bean.RispostaBean;

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


    public DomandaBean select(int id){

        // Specifichiamo le colonne che ci interessano
        String[] projection = {
                COLUMN_ID,
                COLUMN_TESTO,
                COLUMN_ID_DOMANDA
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

            DomandaBean domanda = new DomandaBean();
            DomandaDAO domandaDAO = new DomandaDAO(dbHelper);
            domandaDAO.open();
            domanda = domandaDAO.select(cursor.getInt(2));
            if (domanda!=null){
                risposta.setDomanda(domanda);
                return domanda;
            }
            return null;
        }
        return null;
    }

    public boolean insert(RispostaBean risposta){

        ContentValues values= new ContentValues();
        int id;

        if (risposta.getTesto() != null && risposta.getDomanda() != null) {

            if ((id=doRetrieveMaxId())>0)
                id++;
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
            return cursor.getInt(0);
        }

        return -1;

    }


    private static final String TABLE_NAME = "Risposta";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TESTO = "testo";
    private static final String COLUMN_ID_DOMANDA = "id_domanda";

    private DBHelper dbHelper;
    private SQLiteDatabase database;

}
