package com.example.irepeat.Bean;

import com.example.irepeat.DAO.DBHelper;
import com.example.irepeat.DAO.DomandaDAO;

import java.util.ArrayList;

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

    public QuizBean(){}


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


    public boolean checkQuiz(){

        if (descrizione==null || nome==null || disciplina==null || durata==null || utente==null){
           return false;
        }

        if (visibilita!=0 && visibilita!=1)
            return false;

        if (preferito!=0 && preferito!=1)
            return false;

        return true;
    }

    public ArrayList<DomandaBean> getDomande() {
        return domande;
    }

    public boolean setDomande(DBHelper db) {
        if(this.id>0){
            DomandaDAO domandaDAO = new DomandaDAO(db);
            domandaDAO.open();
            if ((this.domande=domandaDAO.selectByQuiz(this))!=null) {
                domandaDAO.close();
                return true;
            }
            domandaDAO.close();
        }
        return false;
    }

    public void setDomande(ArrayList<DomandaBean> domande) {
        this.domande=domande;
    }

    @Override
    public String toString() {
        String s= "QuizBean{" +
                "id=" + id +
                ", descrizione='" + descrizione + '\'' +
                ", nome='" + nome + '\'' +
                ", disciplina='" + disciplina + '\'' +
                ", preferito=" + preferito +
                ", durata='" + durata + '\'' +
                ", visibilita=" + visibilita +
                ", utente=" + utente.getId()+
                ", id domande= [";

        if(domande!=null) {
            for (DomandaBean d : domande) {
                s += d.getId();
                s += ", ";
            }
        }

        s+="] };";
        return s;
    }

    public void removeDomanda (DomandaBean d){
        this.domande.remove(d);
    }

    private int id;
    private String descrizione;
    private String nome;
    private String disciplina;
    private int preferito; //0= FALSE, 1= TRUE
    private String durata;
    private int visibilita; //0= FALSE, 1= TRUE
    private UtenteBean utente;
    private ArrayList<DomandaBean> domande;
}
