package com.example.jeconomy.models;

import com.orm.SugarRecord;

import java.util.Date;

public class Receita extends SugarRecord {

    private double porcentDescon;
    private double precoDescon;
    private double precoTotal;
    private Date dataReceita;
    private Date dataPag;
    private String formaPamento;
    private Date dataVenda;
    private Date dataServ;
    private Usuario usuario;
    private Categoria categoria;

    public Receita(){

    }

    public Receita(double porcentDescon, double precoDescon, double precoTotal, Date dataReceita,
                   Date dataPag, String formaPamento, Date dataVenda, Date dataServ, Usuario usuario,
                   Categoria categoria){
        this.porcentDescon = porcentDescon;
        this.precoDescon = precoDescon;
        this.precoTotal = precoTotal;
        this.dataReceita = dataReceita;
        this.dataPag = dataPag;
        this.formaPamento = formaPamento;
        this.dataVenda = dataVenda;
        this.dataServ = dataServ;
        this.usuario = usuario;
        this.categoria = categoria;
    }

    public double getPorcentDescon() {
        return porcentDescon;
    }

    public void setPorcentDescon(double porcentDescon) {
        this.porcentDescon = porcentDescon;
    }

    public double getPrecoDescon() {
        return precoDescon;
    }

    public void setPrecoDescon(double precoDescon) {
        this.precoDescon = precoDescon;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }

    public Date getDataReceita() {
        return dataReceita;
    }

    public void setDataReceita(Date dataReceita) {
        this.dataReceita = dataReceita;
    }

    public Date getDataPag() {
        return dataPag;
    }

    public void setDataPag(Date dataPag) {
        this.dataPag = dataPag;
    }

    public String getFormaPamento() {
        return formaPamento;
    }

    public void setFormaPamento(String formaPamento) {
        this.formaPamento = formaPamento;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Date getDataServ() {
        return dataServ;
    }

    public void setDataServ(Date dataServ) {
        this.dataServ = dataServ;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
