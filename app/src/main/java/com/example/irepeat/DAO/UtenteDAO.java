package com.example.irepeat.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.irepeat.Bean.UtenteBean;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class UtenteDAO implements BaseColumns {

    public UtenteDAO(DBHelper dbHelper){
        this.dbHelper =dbHelper;
    }

    public void open(){
        database= dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public UtenteBean select(String email, String password){

        // Specifichiamo le colonne che ci interessano
        String[] projection = {
                COLUMN_EMAIL,
                COLUMN_BIO,
                COLUMN_NOME,
                COLUMN_COGNOME,
                COLUMN_NICKNAME,
                COLUMN_PASSWORD
        };


        // Definiamo la parte 'where' della query.
        // es. selection="EMAIL = ? AND PASSWORD = ?(crittografata)"
        String selection;
        selection = "("+COLUMN_EMAIL + " = ? or "+ COLUMN_NICKNAME+"= ?) "
                + " and "
                +COLUMN_PASSWORD+ " = ?";


        // Specifchiamo gli argomenti per i segnaposto (ovvero i ? nella stringa selection)
        String[] selectionArgs = new String[0];
        try {
            selectionArgs = new String[]{email, email, this.saveEncryptedPassword(password)};
        } catch (NoSuchAlgorithmException e) {
            return null;
        }


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
            UtenteBean utente= new UtenteBean();
            utente.setEmail(cursor.getString(0));
            utente.setBio(cursor.getString(1));
            utente.setNome(cursor.getString(2));
            utente.setCognome(cursor.getString(3));
            utente.setNickname(cursor.getString(4));
            utente.setPassword(cursor.getString(5));
            return utente;
        }

        return null;
    }

    public UtenteBean doRetrieveByEmail(String email){

        // Specifichiamo le colonne che ci interessano
        String[] projection = {
                COLUMN_EMAIL,
                COLUMN_BIO,
                COLUMN_NOME,
                COLUMN_COGNOME,
                COLUMN_NICKNAME,
                COLUMN_PASSWORD
        };


        // Definiamo la parte 'where' della query.
        // es. selection="EMAIL = ? AND PASSWORD = ?(crittografata)"
        String selection;
        selection = COLUMN_EMAIL + " = ? ";


        // Specifchiamo gli argomenti per i segnaposto (ovvero i ? nella stringa selection)
        String[] selectionArgs = {email};

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
            UtenteBean utente= new UtenteBean();
            utente.setEmail(cursor.getString(0));
            utente.setBio(cursor.getString(1));
            utente.setNome(cursor.getString(2));
            utente.setCognome(cursor.getString(3));
            utente.setNickname(cursor.getString(4));
            utente.setPassword(cursor.getString(5));
            return utente;
        }

        return null;
    }


    public boolean insert(UtenteBean utente){

        ContentValues values= new ContentValues();

        if (utente.checkUtente()) {
            values.put(COLUMN_EMAIL, utente.getEmail());
            values.put(COLUMN_NOME, utente.getNome());
            values.put(COLUMN_COGNOME, utente.getCognome());
            values.put(COLUMN_NICKNAME, utente.getNickname());
            values.put(COLUMN_PASSWORD, utente.getPassword());

            if (utente.getBio()!=null){
                values.put(COLUMN_BIO, utente.getBio());
            }

            long check= database.insert(TABLE_NAME, null, values);

            if (check>0)
                return true;
            return false;

        }

        return false;

    }

    public boolean update(UtenteBean utente){

        ContentValues values= new ContentValues();

        if (utente.checkUtente()) {
            values.put(COLUMN_EMAIL, utente.getEmail());
            values.put(COLUMN_NOME, utente.getNome());
            values.put(COLUMN_COGNOME, utente.getCognome());
            values.put(COLUMN_NICKNAME, utente.getNickname());
            values.put(COLUMN_PASSWORD, utente.getPassword());

            if (utente.getBio() != null) {
                values.put(COLUMN_BIO, utente.getBio());
            }

            // es. selection="EMAIL = ?"
            String selection;
            selection = COLUMN_EMAIL + " = ?";


            // Specifchiamo gli argomenti per i segnaposto (ovvero i ? nella stringa selection)
            String[] selectionArgs = {utente.getEmail()};

            int check= database.update(TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);

            if (check==1)
                return true;
            return false;

        }

        return false;

    }

    public boolean delete(UtenteBean utente){

        int check= database.delete(TABLE_NAME,
                COLUMN_EMAIL+" = ?",
                new String[]{String.valueOf(utente.getEmail())});

        if (check==0)
            return false;
        return true;

    }

    public String saveEncryptedPassword(String passwordUtente) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        byte[] hashedPwd = digest.digest(passwordUtente.getBytes(StandardCharsets.UTF_8));
        StringBuilder builder = new StringBuilder();
        for(byte bit: hashedPwd){
            builder.append(String.format("%02x", bit));
        }
        return builder.toString();
    }



    private static final String TABLE_NAME = "Utente";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_BIO = "bio";
    private static final String COLUMN_NOME = "nome";
    private static final String COLUMN_COGNOME = "cognome";
    private static final String COLUMN_NICKNAME = "nickname";
    private static final String COLUMN_PASSWORD = "password";

    private DBHelper dbHelper;
    private SQLiteDatabase database;
}
