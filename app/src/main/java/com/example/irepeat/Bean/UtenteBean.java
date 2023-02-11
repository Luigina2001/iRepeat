package com.example.irepeat.Bean;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class UtenteBean {

    public UtenteBean(String password, String bio, String nome, String cognome, String nickname) {
        try {
            this.password = this.saveEncryptedPassword(password);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException");
        }
        this.bio = bio;
        this.nome = nome;
        this.cognome = cognome;
        this.nickname = nickname;
    }

    public UtenteBean(){}

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
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", bio='" + bio + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }

    public boolean checkUtente(){
        if (password==null || nome==null || cognome==null || nickname==null)
            return false;
        return true;
    }

    public String saveEncryptedPassword(String passwordUtente) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        byte[] hashedPwd = digest.digest(passwordUtente.getBytes(StandardCharsets.UTF_8));
        StringBuilder builder = new StringBuilder();
        for(byte bit: hashedPwd){
            builder.append(String.format("%02x", bit));
        }
        return builder.toString();
    }


    /*private String saveEncryptedPassword(String password) {
        try {
            SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedPassword = cipher.doFinal(password.getBytes());
            String hexEncryptedPassword = bytesToHex(encryptedPassword);

            return hexEncryptedPassword;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String password;
    private String bio;
    private String nome;
    private String cognome;
    private String nickname;
}
