package com.example.irepeat.Bean;

public class DomandaBean {

    public DomandaBean() {
    }

    public DomandaBean(int id, String testo, QuizBean quiz) {
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

    private int id;
    private String testo;
    private QuizBean quiz;
}
