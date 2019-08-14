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

    public Despesa(){

    }

    public Despesa(boolean isPago, char formaPag, Date dataPag, Date dataVenc, double valor, Categoria categoria) {
        this.isPago = isPago;
        this.formaPag = formaPag;
        this.dataPag = dataPag;
        this.dataVenc = dataVenc;
        this.valor = valor;
        this.categoria = categoria;
    }

    public boolean isPago() {
        return isPago;
    }

    public double getValor() {
        return valor;
    }
}
