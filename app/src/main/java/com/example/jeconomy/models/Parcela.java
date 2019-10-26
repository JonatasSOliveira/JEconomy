package com.example.jeconomy.models;

import com.orm.SugarRecord;

import java.util.Date;

public class Parcela extends SugarRecord {
    private Date dataPag;
    private Date dataVenc;
    private int nParcela;
    private Receita receita;
    private Despesa despesa;

    public Parcela(){

    }

    public Parcela(Date dataPag, Date dataVenc, int nParcela, Receita receita) {
        this.dataPag = dataPag;
        this.dataVenc = dataVenc;
        this.nParcela = nParcela;
        this.receita = receita;
        despesa = null;
    }

    public Parcela(Date dataPag, Date dataVenc, int nParcela, Despesa despesa) {
        this.dataPag = dataPag;
        this.dataVenc = dataVenc;
        this.nParcela = nParcela;
        this.despesa = despesa;
        receita = null;
    }

    public int getnParcela() {
        return nParcela;
    }

    public Despesa getDespesa() {
        return despesa;
    }

    public Date getDataPag() {
        return dataPag;
    }

    public Date getDataVenc() {
        return dataVenc;
    }
}
