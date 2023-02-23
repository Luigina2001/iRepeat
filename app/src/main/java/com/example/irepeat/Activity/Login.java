package com.example.irepeat.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
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

public class Login extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private String usernameString="", passwordString="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.login_landscape);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.login);
        }

        if (savedInstanceState!=null){
            usernameString=savedInstanceState.getString("username");
            passwordString=savedInstanceState.getString("password");
        }

        username= findViewById(R.id.username);
        username.setText(usernameString);
        password= findViewById(R.id.password);
        password.setText(passwordString);
    }

    public void onClickLogin (View view){

        UtenteDAO dao= new UtenteDAO(new DBHelper(this));
        dao.open();
        UtenteBean utente= dao.select(username.getText().toString(), password.getText().toString());
        dao.close();
        if (utente!=null) {
            Toast.makeText(this, "Login effettuato con successo", Toast.LENGTH_LONG).show();
            MyPreferences preferences= new MyPreferences(this);
            preferences.setLoggedIn(true, utente.getId());
            Intent i= new Intent(this, Homepage.class);
            startActivity(i);
        }
        else{
            Toast.makeText(this, "Credenziali errate", Toast.LENGTH_LONG).show();
        }

    }

    public void onClickRegistrazione (View view){
        Intent i= new Intent(this, Registrazione.class);
        startActivity(i);
    }

    public void onClickBack(View view){
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("username", username.getText().toString());
        outState.putString("password", password.getText().toString());
        super.onSaveInstanceState(outState);
    }
}
