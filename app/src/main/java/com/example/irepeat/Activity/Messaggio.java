package com.example.irepeat.Activity;

import java.io.Serializable;

public class Messaggio implements Serializable {

    public Messaggio(){

    }

    public Messaggio(String testo, String utente, String dataOra, boolean isMine) {
        this.testo = testo;
        this.utente = utente;
        this.ora = dataOra;
        this.isMine= isMine;
    }

    public String getTesto() {
        return testo;
    }

    public String getUtente() {
        return utente;
    }

    public String getOra() {
        return ora;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public void setUtente(String utente) {
        this.utente = utente;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }


    private String testo;
    private String utente;
    private String ora;
    private boolean isMine;
    private int id;
}
