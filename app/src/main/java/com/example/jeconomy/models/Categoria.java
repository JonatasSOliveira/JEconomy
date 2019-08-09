package com.example.jeconomy.models;

import com.orm.SugarRecord;

public class Categoria extends SugarRecord {

    private String nome;
    private boolean isUsed;

    public Categoria(){

    }

    public Categoria(String nome){
        this.nome = nome;
        this.isUsed = false;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
