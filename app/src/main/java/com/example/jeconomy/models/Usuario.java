package com.example.jeconomy.models;

import com.orm.SugarRecord;

public class Usuario extends SugarRecord {

    private String nome;
    private String contato;
    private String login;
    private String senha;

    public Usuario(){

    }

    public Usuario(String nome, String contato, String login, String senha){
        this.nome = nome;
        this.contato = contato;
        this.login = login;
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }
}
