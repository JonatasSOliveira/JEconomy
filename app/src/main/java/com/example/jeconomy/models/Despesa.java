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

    public Despesa(){

    }

    public Despesa(double valor, Categoria categoria, String obs, Usuario usuario) {
        this.valor = valor;
        this.categoria = categoria;
        this.usuario = usuario;
        this.obs = obs;
        dataPag = null;
        dataVenc = null;
        formaPag = "";
    }

    public void setFormaPag(String formaPag) {
        this.formaPag = formaPag;
    }

    public void setDataPag(Date dataPag) {
        this.dataPag = dataPag;
    }

    public void setDataVenc(Date dataVenc) {
        this.dataVenc = dataVenc;
    }

    public void setPago(boolean pago) {
        isPago = pago;
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
}
