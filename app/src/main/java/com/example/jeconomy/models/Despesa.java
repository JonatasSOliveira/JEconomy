package com.example.jeconomy.models;

import com.orm.SugarRecord;

import java.util.Date;

public class Despesa extends SugarRecord {
    private char formaPag;
    private Date dataPag;
    private Date dataVenc;
    private double valor;
    private boolean isPago;
    private Categoria categoria;
    private Usuario usuario;

    public Despesa(){

    }

    public Despesa(double valor, Categoria categoria, Usuario usuario) {
        this.valor = valor;
        this.categoria = categoria;
        this.usuario = usuario;
        dataPag = null;
        dataVenc = null;
        formaPag = '0';
    }

    public void setFormaPag(char formaPag) {
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
}
