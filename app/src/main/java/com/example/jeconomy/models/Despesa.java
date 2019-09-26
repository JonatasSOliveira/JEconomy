package com.example.jeconomy.models;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Date;

public class Despesa extends SugarRecord implements Serializable {
    private String formaPag;
    private Date dataPag;
    private Date dataVenc;
    private double valor;
    private boolean isPago;
    private Categoria categoria;
    private Usuario usuario;
    private String obs;
    private int qtdeParcela;

    public Despesa(){

    }

    public Despesa(double valor, Categoria categoria, String obs, int qtdeParcela, Usuario usuario) {
        this.valor = valor;
        this.categoria = categoria;
        this.usuario = usuario;
        this.obs = obs;
        this.qtdeParcela = qtdeParcela;
        dataPag = null;
        dataVenc = null;
        formaPag = "";
    }

    public void setFormaPag(String formaPag) {
        this.formaPag = formaPag;
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

    public Date getDataPag() {
        return dataPag;
    }

    public Date getDataVenc() {
        return dataVenc;
    }

    public String getFormaPag() {
        return formaPag;
    }

    public void setPagVenc(boolean isPago, Date data){
        this.isPago = isPago;
        if(isPago){
            this.dataPag = data;
        }else {
            this.dataVenc = data;
        }
    }
}
