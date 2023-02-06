package com.example.irepeat.Bean;

public class QuizBean {

    public QuizBean(String descrizione, String nome, String disciplina, int preferito, String durata, int visibilita, UtenteBean utente) {
        this.descrizione = descrizione;
        this.nome = nome;
        this.disciplina = disciplina;
        this.preferito = preferito;
        this.durata = durata;
        this.visibilita = visibilita;
        this.utente = utente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public int getPreferito() {
        return preferito;
    }

    public void setPreferito(int preferito) {
        this.preferito = preferito;
    }

    public String getDurata() {
        return durata;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }

    public int getVisibilita() {
        return visibilita;
    }

    public void setVisibilita(int visibilita) {
        this.visibilita = visibilita;
    }

    public UtenteBean getUtente() {
        return utente;
    }

    public void setUtente(UtenteBean utente) {
        this.utente = utente;
    }

    @Override
    public String toString() {
        return "QuizBean{" +
                "id=" + id +
                ", descrizione='" + descrizione + '\'' +
                ", nome='" + nome + '\'' +
                ", disciplina='" + disciplina + '\'' +
                ", preferito=" + preferito +
                ", durata='" + durata + '\'' +
                ", visibilita=" + visibilita +
                ", utente='" + utente.getEmail() + '\'' +
                '}';
    }

    private int id;
    private String descrizione;
    private String nome;
    private String disciplina;
    private int preferito; //0= FALSE, 1= TRUE
    private String durata;
    private int visibilita; //0= FALSE, 1= TRUE
    private UtenteBean utente;
}
