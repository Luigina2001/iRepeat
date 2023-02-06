package com.example.irepeat.Bean;

public class RispostaBean {

    public RispostaBean() {
    }

    public RispostaBean(int id, String testo, DomandaBean domanda) {
        this.id = id;
        this.testo = testo;
        this.domanda = domanda;
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

    private int id;
    private String testo;
    private DomandaBean domanda;
}
