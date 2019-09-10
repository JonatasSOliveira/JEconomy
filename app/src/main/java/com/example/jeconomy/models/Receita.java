package com.example.jeconomy.models;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Date;

public class Receita extends SugarRecord implements Serializable {

    private String tipoReceita;
    private Categoria categoria;
    private double valor;
    private double desconto;
    private double valorTotal;
    private Date dataVend;
    private Date dataPag;
    private Date dataVenc;
    private String formaPagamento;
    private Date dataServ;
    private Usuario usuario;
    private boolean isPago;


    public Receita() {

    }

    public Receita(Categoria categoria, double valor, double desconto, double valorTotal, Usuario usuario) {
        this.categoria = categoria;
        this.valor = valor;
        this.desconto = desconto;
        this.valorTotal = valorTotal;
        this.usuario = usuario;
        dataVenc = null;
        dataPag = null;
        dataServ = null;
        dataVend = null;
        formaPagamento = "0";
    }

    public void setTipoReceita(String tipoReceita) {
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

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public void setDataServ(Date dataServ) {
        this.dataServ = dataServ;
    }

    public void setPago(boolean pago) {
        isPago = pago;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public boolean isPago() {
        return isPago;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Date getDataPag() {
        return dataPag;
    }

    public double getDesconto() {
        return desconto;
    }

    public Date getDataVenc() {
        return dataVenc;
    }

    public double getValor() {
        return valor;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public String getTipoReceita() {
        return tipoReceita;
    }

    public Date getDataVend() {
        return dataVend;
    }

    public Date getDataServ() {
        return dataServ;
    }
}
