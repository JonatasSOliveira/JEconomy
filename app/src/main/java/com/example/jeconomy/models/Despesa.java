package com.example.jeconomy.models;

import com.orm.SugarRecord;

import java.io.Serializable;

public class Despesa extends SugarRecord implements Serializable {
    private double valor;
    private boolean isPago;
    private String obs;
    private int qtdeParcela;
    private Usuario usuario;
    private Categoria categoria;

    public Despesa(){

    }

    public Despesa(double valor, String obs, int qtdeParcela, Categoria categoria, Usuario usuario) {
        this.valor = valor;
        this.categoria = categoria;
        this.usuario = usuario;
        this.obs = obs;
        this.qtdeParcela = qtdeParcela;
    }

    public boolean isPago() {
        return isPago;
    }

    public double getValor() {
        return valor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    /*public void setPagVenc(boolean isPago, Date data){
        this.isPago = isPago;
        if(isPago){
            this.dataPag = data;
        }else {
            this.dataVenc = data;
        }
    }*/
}
