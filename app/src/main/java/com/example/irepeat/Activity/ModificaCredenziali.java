package com.example.irepeat.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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

public class ModificaCredenziali extends AppCompatActivity {

    UtenteBean utenteBean = new UtenteBean();
    MyPreferences preference;
    EditText n, b, pa, pn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.modifica_credenziali_landscape);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.modifica_credenziali);
        }

        preference = new MyPreferences(this);
        int id = Integer.parseInt(preference.getId());

        UtenteDAO utenteDAO = new UtenteDAO(new DBHelper(getApplicationContext()));

        utenteDAO.open();
        utenteBean = utenteDAO.doRetrieveById(id);
        utenteDAO.close();

        String pswAttuale ="", pswNuova ="";
        String nickname = utenteBean.getNickname(), bio = utenteBean.getBio();

        if(savedInstanceState != null){
            pswAttuale=savedInstanceState.getString("passwordAttuale");
            pswNuova=savedInstanceState.getString("passwordNuova");
            nickname = savedInstanceState.getString("nickname");
            bio = savedInstanceState.getString("bio");
        }

        n = findViewById(R.id.nickname);
        b = findViewById(R.id.bio);
        pa = findViewById(R.id.passwordAttuale);
        pn = findViewById(R.id.passwordNuova);

        n.setText(nickname);
        b.setText(bio);
        pa.setText(pswAttuale);
        pn.setText(pswNuova);

    }




    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("nickname", n.getText().toString());
        outState.putString("bio", b.getText().toString());
        outState.putString("passwordAttuale", pa.getText().toString());
        outState.putString("passwordNuova", pn.getText().toString());
        super.onSaveInstanceState(outState);
    }

    public void onClickBack(View view){
        super.onBackPressed();
    }

    public void onClickModificaCredenziali(View view) throws NoSuchAlgorithmException {


        String nickname = String.valueOf(n.getText());
        String bio = String.valueOf(b.getText());
        String passwordAttuale = String.valueOf(pa.getText());
        String passwordNuova = String.valueOf(pn.getText());

        String message;
        String passwordCrittografata = null;
        boolean ris = false;


        if(isNickname(nickname)){
            if(bio != null){
                if(isPassword(passwordAttuale)){
                    passwordCrittografata = getPasswordCrittografata(passwordAttuale);
                    if(passwordCrittografata.equals(utenteBean.getPassword()))
                        if(isPassword(passwordNuova)){
                            utenteBean.setPassword(getPasswordCrittografata(passwordNuova));
                            utenteBean.setNickname(nickname);
                            utenteBean.setBio(bio);
                            UtenteDAO utenteDAO = new UtenteDAO(new DBHelper(getApplicationContext()));
                            utenteDAO.open();
                            //Log.d("MYDEBUG", utenteBean.toString());
                            ris = utenteDAO.update(utenteBean);
                            utenteDAO.close();

                            if(ris)
                                message = "Modifica effettuata con successo";
                            else
                                message = "Modifica non avvenuta. Riprovare";

                        }else
                            message = "Password deve contenere almeno 8 caratteri";
                    else
                        message = "Password attuale inserita errata 1";
                }else
                    message = "Password attuale inserita errata";
            }else
                message = "Inserire bio";
        }else
            message = "Inserire un nickname valido";

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

        if(ris){
            Intent i= new Intent(this, Homepage.class);
            startActivity(i);
        }




    }

    private boolean isNickname(String nickname) {
        Pattern pattern = Pattern.compile("^[A-z0-9'._#& ]{3,30}$");
        Matcher matcher = pattern.matcher(nickname);
        return matcher.matches();
    }

    private boolean isPassword(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
        Matcher matcher = pattern.matcher(password);
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
