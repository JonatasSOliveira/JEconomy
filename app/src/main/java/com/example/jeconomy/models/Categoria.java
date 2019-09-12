package com.example.jeconomy.models;

import com.orm.SugarRecord;

import java.io.Serializable;

public class Categoria extends SugarRecord implements Serializable {

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

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }
}