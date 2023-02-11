package com.example.irepeat.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.irepeat.Bean.QuizBean;
import com.example.irepeat.Bean.UtenteBean;

import java.util.ArrayList;

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

        if (cursor.getCount()>0){
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
            utente= dao.doRetrieveById(cursor.getInt(7));
            if (utente!=null){
                quiz.setUtente(utente);
                return quiz;
            }
            return null;
        }
        return null;
    }

    public ArrayList<QuizBean> selectByUtente(UtenteBean utente){

        ArrayList<QuizBean> quizUtente= new ArrayList<>();

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
        selection = COLUMN_UTENTE + " = ? ";


        // Specifchiamo gli argomenti per i segnaposto (ovvero i ? nella stringa selection)
        String[] selectionArgs = {String.valueOf(utente.getId())};

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

        if (cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                QuizBean quiz = new QuizBean();
                quiz.setId(cursor.getInt(0));
                quiz.setDescrizione(cursor.getString(1));
                quiz.setNome(cursor.getString(2));
                quiz.setDisciplina(cursor.getString(3));
                quiz.setPreferito(cursor.getInt(4));
                quiz.setDurata(cursor.getString(5));
                quiz.setVisibilita(cursor.getInt(6));
                quiz.setUtente(utente);
                quizUtente.add(quiz);
            }
            while (cursor.moveToNext());
        }
        else
            return null;
        return quizUtente;
    }


    public boolean insert(QuizBean quiz){

        ContentValues values= new ContentValues();
        int id;

        if (quiz.checkQuiz()) {

            if ((id=doRetrieveMaxId())>=0) {
                id++;
                quiz.setId(id);
            }
            else
                return false;
            values.put(COLUMN_ID, id);
            values.put(COLUMN_DESCRIZIONE, quiz.getDescrizione());
            values.put(COLUMN_NOME, quiz.getNome());
            values.put(COLUMN_DISCIPLINA, quiz.getDisciplina());
            values.put(COLUMN_PREFERITO, quiz.getPreferito());
            values.put(COLUMN_DURATA, quiz.getDurata());
            values.put(COLUMN_VISIBILITA, quiz.getVisibilita());
            values.put(COLUMN_UTENTE, quiz.getUtente().getId());

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

    public ArrayList<QuizBean> selectByNome(String nome){

        ArrayList<QuizBean> quizUtente= new ArrayList<>();

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
        selection = COLUMN_NOME + " %LIKE% ? ";


        // Specifchiamo gli argomenti per i segnaposto (ovvero i ? nella stringa selection)
        String[] selectionArgs = {nome};

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

        if (cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                QuizBean quiz = new QuizBean();
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
                utente= dao.doRetrieveById(cursor.getInt(7));
                if (utente!=null){
                    quiz.setUtente(utente);
                    quizUtente.add(quiz);
                }
                else
                    return null;
            }
            while (cursor.moveToNext());
        }
        else
            return null;
        return quizUtente;
    }


    public ArrayList<QuizBean> selectByDisciplina(String disciplina){

        ArrayList<QuizBean> quizUtente= new ArrayList<>();

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
        selection = COLUMN_NOME + " %LIKE% ? ";


        // Specifchiamo gli argomenti per i segnaposto (ovvero i ? nella stringa selection)
        String[] selectionArgs = {disciplina};

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

        if (cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                QuizBean quiz = new QuizBean();
                quiz.setId(cursor.getInt(0));
                quiz.setDescrizione(cursor.getString(1));
                quiz.setNome(cursor.getString(2));
                quiz.setDisciplina(cursor.getString(3));
                quiz.setPreferito(cursor.getInt(4));
                quiz.setDurata(cursor.getString(5));
                quiz.setVisibilita(cursor.getInt(6));
                //da modificare
                //quiz.setUtente(cursor.getInt(7));
                quizUtente.add(quiz);
            }
            while (cursor.moveToNext());
        }
        else
            return null;
        return quizUtente;
    }

    public boolean delete (QuizBean quiz){
        String [] s= {String.valueOf(quiz.getId())};
        int res= database.delete(TABLE_NAME, COLUMN_ID+"= ?", s);
        if (res==0)
            return false;
        return true;
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
