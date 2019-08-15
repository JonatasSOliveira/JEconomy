package com.example.jeconomy.models;

import com.orm.SugarRecord;

import java.util.Date;

public class Receita extends SugarRecord {

    private char tipoReceita;
    private Categoria categoria;
    private double preco;
    private double porcentDescon;
    private double precoTotal;
    private Date dataVend;
    private Date dataPag;
    private Date dataVenc;
    private char formaPamento;
    private Date dataServ;
    private Usuario usuario;
    private boolean isPago;


    public Receita() {

    }

    public Receita(Categoria categoria, double preco, double porcentDescon, double precoTotal, Usuario usuario) {
        this.categoria = categoria;
        this.preco = preco;
        this.porcentDescon = porcentDescon;
        this.precoTotal = precoTotal;
        this.usuario = usuario;
        dataVenc = null;
        dataPag = null;
        dataServ = null;
        dataVend = null;
        formaPamento = '0';
    }

    public void setTipoReceita(char tipoReceita) {
        this.tipoReceita = tipoReceita;
    }

    public void setDataVend(Date dataVend) {
        this.dataVend = dataVend;
    }

    public void setDataPag(Date dataPag) {
        this.dataPag = dataPag;
    }

    public void setDataVenc(Date dataVenc) {
        this.dataVenc = dataVenc;
    }

    public void setFormaPamento(char formaPamento) {
        this.formaPamento = formaPamento;
    }

    public void setDataServ(Date dataServ) {
        this.dataServ = dataServ;
    }

    public void setPago(boolean pago) {
        isPago = pago;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public boolean isPago() {
        return isPago;
    }
}
