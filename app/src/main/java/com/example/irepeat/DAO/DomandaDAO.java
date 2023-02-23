package com.example.irepeat.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.irepeat.Bean.DomandaBean;
import com.example.irepeat.Bean.QuizBean;

import java.util.ArrayList;

public class DomandaDAO {

    public DomandaDAO(DBHelper dbHelper){
        this.dbHelper =dbHelper;
    }

    public void open(){
        database= dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }


    public DomandaBean select(int id){

        if(id<1)
            return null;

        // Specifichiamo le colonne che ci interessano
        String[] projection = {
                COLUMN_ID,
                COLUMN_TESTO,
                COLUMN_QUIZ
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

        if (cursor.getCount()>0){
            cursor.moveToFirst();

            DomandaBean domanda = new DomandaBean();
            domanda.setId(cursor.getInt(0));
            domanda.setTesto(cursor.getString(1));

            QuizBean quiz = new QuizBean();
            QuizDAO quizDAO= new QuizDAO(dbHelper);
            quizDAO.open();
            quiz = quizDAO.select(cursor.getInt(2));
            if (quiz!=null){
                domanda.setQuiz(quiz);
                return domanda;
            }
            return null;
        }
        return null;
    }

    public ArrayList<DomandaBean> selectByQuiz(QuizBean quiz){

        ArrayList<DomandaBean> domande= new ArrayList<>();

        // Specifichiamo le colonne che ci interessano
        String[] projection = {
                COLUMN_ID,
                COLUMN_TESTO,
                COLUMN_QUIZ
        };


        // Definiamo la parte 'where' della query.
        // es. selection="ID = ? "
        String selection;
        selection = COLUMN_QUIZ + " = ? ";


        // Specifichiamo gli argomenti per i segnaposto (ovvero i ? nella stringa selection)
        String[] selectionArgs = {String.valueOf(quiz.getId())};

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

                DomandaBean domanda = new DomandaBean();
                domanda.setId(cursor.getInt(0));
                domanda.setTesto(cursor.getString(1));

                QuizBean quiz1 = new QuizBean();
                QuizDAO quizDAO= new QuizDAO(dbHelper);
                quizDAO.open();
                quiz1 = quizDAO.select(cursor.getInt(2));
                if (quiz1!=null){
                    domanda.setQuiz(quiz);
                    domande.add(domanda);
                }
                else
                    return null;
            }while (cursor.moveToNext());
        }
        else
            return null;

        return domande;
    }

    public boolean insert(DomandaBean domanda){

        ContentValues values= new ContentValues();
        int id;

        if (domanda.getTesto() != null && domanda.getQuiz() != null) {

            if ((id=doRetrieveMaxId())>=0) {
                id++;
                domanda.setId(id);
            }
            else
                return false;
            values.put(COLUMN_ID, id);
            values.put(COLUMN_TESTO, domanda.getTesto());
            values.put(COLUMN_QUIZ, domanda.getQuiz().getId());

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

    public void doDeleteByQuiz(int idDomanda){
        String query = "DELETE FROM Domanda WHERE id="+idDomanda;
        database.execSQL(query);

    }

    public boolean update(DomandaBean domanda){

        ContentValues values= new ContentValues();
        values.put(COLUMN_ID, domanda.getId());
        values.put(COLUMN_TESTO, domanda.getTesto());
        values.put(COLUMN_QUIZ, domanda.getQuiz().getId());

        // es. selection="EMAIL = ?"
        String selection;
        selection = COLUMN_ID + " = ?";

        // Specifchiamo gli argomenti per i segnaposto (ovvero i ? nella stringa selection)
        String[] selectionArgs = {String.valueOf(domanda.getId())};

        int check= database.update(TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if (check==1)
            return true;
        else return insert(domanda);
    }


    private static final String TABLE_NAME = "Domanda";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TESTO = "testo";
    private static final String COLUMN_QUIZ = "quiz";

    private DBHelper dbHelper;
    private SQLiteDatabase database;
}
