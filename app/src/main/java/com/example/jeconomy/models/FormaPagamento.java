package com.example.jeconomy.models;

import com.orm.SugarRecord;

public class FormaPagamento extends SugarRecord {
    private String tipo;
    private double valor;
    private Parcela parcela;

    public FormaPagamento(){

    }

    public FormaPagamento(double valor, Parcela parcela) {
        this.valor = valor;
        this.parcela = parcela;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}