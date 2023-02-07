package com.example.irepeat.Bean;

import com.example.irepeat.DAO.DBHelper;
import com.example.irepeat.DAO.RispostaDAO;

import java.util.ArrayList;

public class DomandaBean {

    public DomandaBean() {
    }

    public DomandaBean(String testo, QuizBean quiz) {
        this.id = id;
        this.testo = testo;
        this.quiz = quiz;
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

    public QuizBean getQuiz() {
        return quiz;
    }

    public void setQuiz(QuizBean quiz) {
        this.quiz = quiz;
    }

    public ArrayList<RispostaBean> getRisposte() {
        return risposte;
    }

    public boolean setRisposte(DBHelper db) {

        if (this.id>0) {
            RispostaDAO rispostaDAO = new RispostaDAO(db);
            rispostaDAO.open();

            if ((risposte=rispostaDAO.selectByDomanda(this))!=null)
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String s= "DomandaBean{" +
                "id=" + id +
                ", testo='" + testo + '\'' +
                ", quiz=" + quiz.getId() +
                ", risposte= [";

        for (RispostaBean r: risposte){
            s+=r.getId();
            s+=", ";
        }

        s+="] };";
        return s;
    }

    private int id;
    private String testo;
    private QuizBean quiz;
    private ArrayList<RispostaBean> risposte;
}
