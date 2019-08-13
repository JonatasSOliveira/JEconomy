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
}
