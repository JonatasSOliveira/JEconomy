package com.example.jeconomy.models;

import com.orm.SugarRecord;

import java.util.Date;

public class Despesa extends SugarRecord {

    private String nome;
    private String formaPag;
    private Date dataPag;
    private Date dataVenc;
    private double preco;

    public Despesa(){

    }

    public Despesa(String nome, String formaPag, Date dataPag, Date dataVenc, double preco){
        this.nome = nome;
        this.formaPag = formaPag;
        this.dataPag = dataPag;
        this.dataVenc = dataVenc;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFormaPag() {
        return formaPag;
    }

    public void setFormaPag(String formaPag) {
        this.formaPag = formaPag;
    }

    public Date getDataPag() {
        return dataPag;
    }

    public void setDataPag(Date dataPag) {
        this.dataPag = dataPag;
    }

    public Date getDataVenc() {
        return dataVenc;
    }

    public void setDataVenc(Date dataVenc) {
        this.dataVenc = dataVenc;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
