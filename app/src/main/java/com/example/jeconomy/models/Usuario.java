package com.example.jeconomy.models;

import com.orm.SugarRecord;

import java.io.Serializable;

public class Usuario extends SugarRecord implements Serializable {

    private String nome;
    private String login;
    private String senha;

    public Usuario(){

    }

    public Usuario(String nome, String login, String senha){
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }
    public String getNome(){
        return nome;
    }
}
