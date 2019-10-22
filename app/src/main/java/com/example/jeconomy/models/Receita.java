package com.example.jeconomy.models;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Date;

public class Receita extends SugarRecord implements Serializable {
    private String tipoReceita;
    private double valor;
    private double desconto;
    private double valorTotal;
    private Date dataVend;
    private Date dataServ;
    private boolean isPago;
    private String obs;
    private int qtdeParcelas;
    private Categoria categoria;
    private Usuario usuario;

    public Receita() {

    }

    public Receita(double valor, double desconto, double valorTotal, int qtdeParcelas, Categoria categoria, Usuario usuario) {
        this.categoria = categoria;
        this.valor = valor;
        this.desconto = desconto;
        this.valorTotal = valorTotal;
        this.usuario = usuario;
        this.qtdeParcelas = qtdeParcelas;
        dataServ = null;
        dataVend = null;
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

    public Date getDataVend() {
        return dataVend;
    }

    public Date getDataServ() {
        return dataServ;
    }

    public void setVendServ(String tipo, Date data){
        this.tipoReceita = tipo;

        if(this.tipoReceita.equals("V")){
            this.dataVend = data;
        } else{
            this.dataServ = data;
        }
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
