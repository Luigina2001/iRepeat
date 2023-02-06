package com.example.irepeat.Bean;

public class QuizBean {

    public QuizBean(String descrizione, String nome, String disciplina, int preferito, String durata, int visibilita, String emailUtente) {
        this.descrizione = descrizione;
        this.nome = nome;
        this.disciplina = disciplina;
        this.preferito = preferito;
        this.durata = durata;
        this.visibilita = visibilita;
        this.emailUtente = emailUtente;
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

    public String getEmailUtente() {
        return emailUtente;
    }

    public void setEmailUtente(String emailUtente) {
        this.emailUtente = emailUtente;
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
                ", emailUtente='" + emailUtente + '\'' +
                '}';
    }

    private int id;
    private String descrizione;
    private String nome;
    private String disciplina;
    private int preferito; //0= FALSE, 1= TRUE
    private String durata;
    private int visibilita; //0= FALSE, 1= TRUE
    private String emailUtente;
}
