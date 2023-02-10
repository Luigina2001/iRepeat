package com.example.irepeat.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.irepeat.Bean.UtenteBean;
import com.example.irepeat.DAO.DBHelper;
import com.example.irepeat.DAO.UtenteDAO;
import com.example.irepeat.R;
import com.example.irepeat.Utils.MyPreferences;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registrazione extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.registrazione_landscape);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.registrazione);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        //da implementare
        super.onSaveInstanceState(outState);
    }

    public void onClickRegistrazione (View view){

        EditText em = findViewById(R.id.email);
        String email = String.valueOf(em.getText());
        EditText n = findViewById(R.id.nickname);
        String nickname = String.valueOf(n.getText());
        EditText p = findViewById(R.id.password);
        String password = String.valueOf(p.getText());
        EditText rp = findViewById(R.id.repeatPassword);
        String repeatPassword = String.valueOf(rp.getText());
        EditText no = findViewById(R.id.nome);
        String nome = String.valueOf(no.getText());
        EditText c = findViewById(R.id.cognome);
        String cognome = String.valueOf(c.getText());
        EditText b = findViewById(R.id.bio);
        String bio = String.valueOf(b.getText());

        String message;
        String passwordCrittografata = null;
        UtenteBean utenteBean=null;
        boolean ris = false;

        if(isEmail(email)){
            if(isPassword(password)) {
                if (isNome(nome) && isCognome(cognome) && isNickname(nickname)) {

                    if ((password.equals(repeatPassword))) {

                        /*try {
                            passwordCrittografata = getPasswordCrittografata(password);
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }*/

                        utenteBean = new UtenteBean(email,password,bio,nome,cognome, nickname);
                        //da controllare
                        UtenteDAO utenteDAO = new UtenteDAO(new DBHelper(getApplicationContext()));

                        utenteDAO.open();
                        ris = utenteDAO.insert(utenteBean);
                        utenteDAO.close();

                        if(ris)
                            message = "Registrazione avvenuta con successo";
                        else
                            message = "Registrazione non andata a buon fine. Riprova";

                    }else
                        message = "Le password inserite non corrispondono";
                }else
                    message = "Dati inseriti non validi";
            }else
                message = "Password deve contenere almeno 8 caratteri";
        }else
            message = "Email non valida";

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        if(ris && utenteBean.getEmail()!=null){
            MyPreferences preferences= new MyPreferences(this);
            preferences.setLoggedIn(true, utenteBean.getEmail());
            Intent i= new Intent(this, Homepage.class);
            startActivity(i);
        }
        else{
            Toast.makeText(this, "!Errore registrazione utente", Toast.LENGTH_LONG).show();
        }

    }

    public void onClickBack (View view){
        super.onBackPressed();
    }


    private boolean isNickname(String nickname) {
        Pattern pattern = Pattern.compile("^[A-z0-9'._#& ]{3,30}$");
        Matcher matcher = pattern.matcher(nickname);
        return matcher.matches();
    }


    private boolean isEmail(String email) {
        Pattern pattern = Pattern.compile("^[A-z0-9\\.\\+_-]+@[A-z0-9\\._-]+\\.[A-z]{2,6}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    private boolean isPassword(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }


    private boolean isNome(String nome) {
        Pattern pattern = Pattern.compile("^[A-z' ]{3,50}$");
        Matcher matcher = pattern.matcher(nome);
        return matcher.matches();
    }


    private boolean isCognome(String cognome) {
        Pattern pattern = Pattern.compile("^[A-z' ]{3,50}$");
        Matcher matcher = pattern.matcher(cognome);
        return matcher.matches();
    }

    public String getPasswordCrittografata(String passwordUtente) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        byte[] hashedPwd = digest.digest(passwordUtente.getBytes(StandardCharsets.UTF_8));
        StringBuilder builder = new StringBuilder();
        for(byte bit: hashedPwd){
            builder.append(String.format("%02x", bit));
        }
        return builder.toString();
    }

}
