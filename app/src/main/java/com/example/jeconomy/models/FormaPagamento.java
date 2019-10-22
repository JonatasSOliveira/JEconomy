package com.example.jeconomy.models;

import com.orm.SugarRecord;

public class FormaPagamento extends SugarRecord {
    private String tipo;
    private double valor;
    private Parcela parcela;

    public FormaPagamento(){

    }

    public FormaPagamento(String tipo, double valor, Parcela parcela){
        this.tipo = tipo;
        this.valor = valor;
        this.parcela = parcela;
    }
}