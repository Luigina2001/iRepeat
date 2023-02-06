package com.example.irepeat.Bean;

public class UtenteBean {

    public UtenteBean(String email, String password, String bio, String nome, String cognome, String nickname) {
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.nome = nome;
        this.cognome = cognome;
        this.nickname = nickname;
    }

    public UtenteBean(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "UtenteBean{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", bio='" + bio + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }

    private String email;
    private String password;
    private String bio;
    private String nome;
    private String cognome;
    private String nickname;
}
