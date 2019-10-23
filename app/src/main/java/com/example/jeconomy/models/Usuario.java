package com.example.jeconomy.models;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Usuario extends SugarRecord implements Serializable {

    private String nome;
    private String login;
    private String senha;

    public Usuario(){

    }

    public Usuario(String nome, String login, String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        this.nome = nome;
        this.login = login;
        this.senha = setCript(senha);
    }

    public String getSenha() {
        return senha;
    }
    public String getNome(){
        return nome;
    }

    public String setCript(String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        senha = senha + "jeconomy";
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] bmd = md.digest(senha.getBytes("UTF-8"));

        StringBuilder sb = new StringBuilder();

        for(byte b: bmd){
            sb.append(String.format("%02X", 0xFF & b));
        }
        System.out.println("====================> " + sb.toString());

        return sb.toString();
    }

    public boolean eqSenha(String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        senha = setCript(senha);

        return this.senha.equals(senha);
    }
}
