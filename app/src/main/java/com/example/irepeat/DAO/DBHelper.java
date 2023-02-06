package com.example.irepeat.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="iRepeat.db";
    private static final int DB_VERSION= 1;

    public DBHelper(Context context){
        super (context, DB_NAME,null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //TABELLA UTENTE
        String sql= "CREATE TABLE Utente (email TEXT PRIMARY KEY, " +
                "bio TEXT, " +
                "nome TEXT, " +
                "cognome TEXT, " +
                "nickname TEXT, " +
                "password TEXT);";

        db.execSQL(sql);

        //TABELLA QUIZ
        sql= "CREATE TABLE Quiz (id INTEGER PRIMARY KEY, " +
                "descrizione TEXT, " +
                "nome TEXT, " +
                "disciplina TEXT, " +
                "preferito INTEGER, " + //0= FALSE, 1= TRUE
                "durata TEXT, "+
                "visibilita INTEGER, "+ //0= FALSE, 1= TRUE
                "utente TEXT, "+
                "FOREIGN KEY (email) REFERENCES Utente(email));";

        db.execSQL(sql);

        //TABELLA DOMANDA
        sql= "CREATE TABLE Domanda (id INTEGER PRIMARY KEY, " +
                "testo TEXT, "+
                "quiz INTEGER, "+
                "FOREIGN KEY (quiz) REFERENCES Quiz(id));";

        db.execSQL(sql);

        //TABELLA RISPOSTA
        sql= "CREATE TABLE Risposta (id INTEGER PRIMARY KEY, " +
                "testo TEXT, "+
                "domanda INTEGER, "+
                "FOREIGN KEY (domanda) REFERENCES Domanda(id));";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
