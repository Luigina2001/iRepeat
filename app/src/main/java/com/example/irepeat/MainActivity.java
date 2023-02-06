package com.example.irepeat;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.example.irepeat.Bean.UtenteBean;
import com.example.irepeat.DAO.DBHelper;
import com.example.irepeat.DAO.UtenteDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        /*Connection conn= null;
        try {
            conn= DriverManager.getConnection("jdbc:sqlite:iRepeat.db");
            Statement statement= conn.createStatement();
            statement.executeUpdate("DROP DATABASE iRepeat");
            Log.d("MYDEBUG","DB CANCELLATO");
        } catch (SQLException e) {
            Log.d("MYDEBUG","eccezione 1");
        }
        finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    Log.d("MYDEBUG","eccezione 2");
                }
            }
        }*/


        DBHelper db= new DBHelper(this);
        //db.onUpgrade(db.getWritableDatabase(), 1, 4);
        //db= new DBHelper(this);
        //db.deleteDatabase(this);
        //db= new DBHelper(this);
        //db.onCreate(db.getWritableDatabase());
        UtenteDAO utenteDAO= new UtenteDAO(db);
        utenteDAO.open();
        UtenteBean utente= new UtenteBean("luigina@iRepeat.it", "luigina01", "prova bio", "luigina", "costante", "luigina01");
        //utenteDAO.insert(utente);

        utenteDAO.update(utente);

        //Log.d("MYDEBUG", utente.toString());
        Log.d("MYDEBUG", String.valueOf(utenteDAO.select(utente.getEmail(), utente.getPassword())));

        utenteDAO.close();

    }
}