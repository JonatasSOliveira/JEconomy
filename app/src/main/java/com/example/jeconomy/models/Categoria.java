package com.example.jeconomy.models;

import com.orm.SugarRecord;

public class Categoria extends SugarRecord {

    private String nome;

    public Categoria(){

    }

    public Categoria(String nome){
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
