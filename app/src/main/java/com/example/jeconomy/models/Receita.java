package com.example.jeconomy.models;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Date;

public class Receita extends SugarRecord implements Serializable {
    private String tipoReceita;
    private double valor;
    private double desconto;
    private double valorTotal;
    private Date dataServ;
    private boolean isPago;
    private String obs;
    private int qtdeParcelas;
    private Categoria categoria;
    private Usuario usuario;

    public Receita() {

    }

    public Receita(double valor, double desconto, double valorTotal, int qtdeParcelas, Date dataServ, Categoria categoria, Usuario usuario) {
        this.categoria = categoria;
        this.valor = valor;
        this.desconto = desconto;
        this.valorTotal = valorTotal;
        this.usuario = usuario;
        this.qtdeParcelas = qtdeParcelas;
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

    public double getDesconto() {
        return desconto;
    }

    public double getValor() {
        return valor;
    }

    public String getTipoReceita() {
        return tipoReceita;
    }

    public Date getDataServ() {
        return dataServ;
    }

    public void setTipoReceita(String tipoReceita) {
        this.tipoReceita = tipoReceita;
    }
}
