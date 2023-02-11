package com.example.irepeat.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="iRepeat";
    private static final int DB_VERSION= 4;
    private Context context;

    public DBHelper(Context context){

        super (context, DB_NAME,null, DB_VERSION);
        this.context=context;

        SQLiteDatabase db = getReadableDatabase();
        if (!db.isOpen()) {
            db = getWritableDatabase();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //TABELLA UTENTE
        String sql= "CREATE TABLE IF NOT EXISTS Utente (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "bio TEXT, " +
                "nome TEXT, " +
                "cognome TEXT, " +
                "nickname TEXT, " +
                "password TEXT);";

        db.execSQL(sql);

        //TABELLA QUIZ
        sql= "CREATE TABLE IF NOT EXISTS Quiz (id INTEGER PRIMARY KEY, " +
                "descrizione TEXT, " +
                "nome TEXT, " +
                "disciplina TEXT, " +
                "preferito INTEGER, " + //0= FALSE, 1= TRUE
                "durata TEXT, "+
                "visibilita INTEGER, "+ //0= FALSE, 1= TRUE
                "utente INTEGER, "+
                "FOREIGN KEY (utente) REFERENCES Utente(id) ON DELETE CASCADE);";

        db.execSQL(sql);

        //TABELLA DOMANDA
        sql= "CREATE TABLE IF NOT EXISTS Domanda (id INTEGER PRIMARY KEY, " +
                "testo TEXT, "+
                "quiz INTEGER, "+
                "FOREIGN KEY (quiz) REFERENCES Quiz(id) ON DELETE CASCADE);";

        db.execSQL(sql);

        //TABELLA RISPOSTA
        sql= "CREATE TABLE IF NOT EXISTS Risposta (id INTEGER PRIMARY KEY, " +
                "testo TEXT, "+
                "domanda INTEGER, "+
                "corretta INTEGER, "+
                "FOREIGN KEY (domanda) REFERENCES Domanda(id) ON DELETE CASCADE);";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Utente");
        db.execSQL("DROP TABLE IF EXISTS Quiz");
        db.execSQL("DROP TABLE IF EXISTS Domanda");
        db.execSQL("DROP TABLE IF EXISTS Risposta");
        context.deleteDatabase("iRepeat");
        onCreate(db);
    }

}
