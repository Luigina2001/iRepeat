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

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModificaCredenziali extends AppCompatActivity {

    UtenteBean utenteBean = new UtenteBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.modifica_credenziali_landscape);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.modifica_credenziali);
        }

        //METTERE QUESTI EXTRA QUANDO PASSIAMO ALL'ACTIVITY ModificaCredenziali
        Intent i = getIntent();
        String email = i.getStringExtra("email");
        String password = i.getStringExtra("password");

        //da controllare
        UtenteDAO utenteDAO = new UtenteDAO(new DBHelper(getApplicationContext()));

        utenteDAO.open();
        utenteBean = utenteDAO.select(email,password);
        utenteDAO.close();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void onClickBack(View view){
        super.onBackPressed();
    }

    public void onClickModificaCredenziali(View view) throws NoSuchAlgorithmException {

        EditText n = findViewById(R.id.nickname);
        String nickname = String.valueOf(n.getText());
        EditText pa = findViewById(R.id.passwordAttuale);
        String passwordAttuale = String.valueOf(pa.getText());
        EditText pn = findViewById(R.id.passwordNuova);
        String passwordNuova = String.valueOf(pn.getText());

        String message;
        String passwordCrittografata = null;
        boolean ris = false;


        if(isNickname(nickname)){
            if(isPassword(passwordAttuale)){
                passwordCrittografata = getPasswordCrittografata(passwordAttuale);
                if(passwordCrittografata.equals(utenteBean.getPassword()))
                    if(isPassword(passwordNuova)){
                        UtenteDAO utenteDAO = new UtenteDAO(new DBHelper(getApplicationContext()));
                        utenteDAO.open();
                        ris = utenteDAO.update(utenteBean);
                        utenteDAO.close();

                        if(ris)
                            message = "Modifica effettuata con successo";
                        else
                            message = "Modifica non avvenuta. Riprovare";

                    }else
                        message = "Password deve contenere almeno 8 caratteri";
                else
                    message = "Password attuale inserita errata";
            }else
                message = "Password attuale inserita errata";

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
