package com.example.irepeat.Bean;

public class RispostaBean {

    public RispostaBean() {
    }

    public RispostaBean(String testo, DomandaBean domanda, int corretta) {
        this.testo = testo;
        this.domanda = domanda;
        this.corretta=corretta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public DomandaBean getDomanda() {
        return domanda;
    }

    public void setDomanda(DomandaBean domanda) {
        this.domanda = domanda;
    }

    public int getCorretta() {
        return corretta;
    }

    public void setCorretta(int corretta) {
        this.corretta = corretta;
    }

    @Override
    public String toString() {
        return "RispostaBean{" +
                "id=" + id +
                ", testo='" + testo + '\'' +
                ", domanda=" + domanda.getId() +
                ", corretta=" + corretta +
                '}';
    }

    private int id;
    private String testo;
    private DomandaBean domanda;
    private int corretta; //0 se la risposta è corretta, 1 se è errata
}
