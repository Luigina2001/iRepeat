package com.example.irepeat.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.irepeat.Bean.QuizBean;
import com.example.irepeat.Bean.UtenteBean;

public class QuizDAO {

    public QuizDAO(DBHelper dbHelper){
        this.dbHelper =dbHelper;
    }

    public void open(){
        database= dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }


    public QuizBean select(int id){

        // Specifichiamo le colonne che ci interessano
        String[] projection = {
                COLUMN_ID,
                COLUMN_DESCRIZIONE,
                COLUMN_NOME,
                COLUMN_DISCIPLINA,
                COLUMN_PREFERITO,
                COLUMN_DURATA,
                COLUMN_VISIBILITA,
                COLUMN_UTENTE
        };


        // Definiamo la parte 'where' della query.
        // es. selection="ID = ? "
        String selection;
        selection = COLUMN_ID + " = ? ";


        // Specifchiamo gli argomenti per i segnaposto (ovvero i ? nella stringa selection)
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
            QuizBean quiz= new QuizBean();
            quiz.setId(cursor.getInt(0));
            quiz.setDescrizione(cursor.getString(1));
            quiz.setNome(cursor.getString(2));
            quiz.setDisciplina(cursor.getString(3));
            quiz.setPreferito(cursor.getInt(4));
            quiz.setDurata(cursor.getString(5));
            quiz.setVisibilita(cursor.getInt(6));

            UtenteBean utente= new UtenteBean();
            UtenteDAO dao= new UtenteDAO(dbHelper);
            dao.open();
            utente= dao.doRetrieveByEmail(cursor.getString(7));
            if (utente!=null){
                quiz.setUtente(utente);
                return quiz;
            }
            return null;
        }
        return null;
    }

    public boolean insert(QuizBean quiz){

        ContentValues values= new ContentValues();
        int id;

        if (quiz.checkQuiz()) {

            if ((id=doRetrieveMaxId())>0)
                id++;
            else
                return false;
            values.put(COLUMN_ID, id);
            values.put(COLUMN_DESCRIZIONE, quiz.getDescrizione());
            values.put(COLUMN_NOME, quiz.getNome());
            values.put(COLUMN_DISCIPLINA, quiz.getDisciplina());
            values.put(COLUMN_PREFERITO, quiz.getPreferito());
            values.put(COLUMN_DURATA, quiz.getDurata());
            values.put(COLUMN_VISIBILITA, quiz.getVisibilita());
            values.put(COLUMN_UTENTE, quiz.getUtente().getEmail());

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


    private static final String TABLE_NAME = "Quiz";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DESCRIZIONE = "descrizione";
    private static final String COLUMN_NOME = "nome";
    private static final String COLUMN_DISCIPLINA = "disciplina";
    private static final String COLUMN_PREFERITO = "preferito";
    private static final String COLUMN_DURATA = "durata";
    private static final String COLUMN_VISIBILITA = "visibilita";
    private static final String COLUMN_UTENTE = "utente";



    private DBHelper dbHelper;
    private SQLiteDatabase database;
}