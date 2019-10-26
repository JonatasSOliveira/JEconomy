package com.example.jeconomy.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.jeconomy.R;
import com.example.jeconomy.models.Receita;

import java.text.DateFormat;

public class ReceitaDialog extends AppCompatDialogFragment {
    private TextView tvCategoria, tvDataPag, tvDataVenc, tvValor, tvDescon, tvValorTotal, tvFormaPag,
            tvTipo, tvDataSV, tvInfoDataSV;
    private Receita receita;

    public ReceitaDialog(Receita receita) {
        this.receita = receita;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_receita, null);

        builder.setView(view).setTitle("Receita").setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        tvCategoria = view.findViewById(R.id.tv_infocategoria_receitadialog);
        tvDataPag = view.findViewById(R.id.tv_infodatapaga_receitadialog);
        tvDataVenc = view.findViewById(R.id.tv_infodatavenc_receitadialog);
        tvDescon = view.findViewById(R.id.tv_infodesconto_receitadialog);
        tvFormaPag = view.findViewById(R.id.tv_infoformapag_receitadialog);
        tvValor = view.findViewById(R.id.tv_infovalor_receitadialog);
        tvValorTotal = view.findViewById(R.id.tv_infovalortotal_receitadialog);
        tvTipo = view.findViewById(R.id.tv_infotipo_receitadialog);
        tvDataSV = view.findViewById(R.id.tv_dataservvenda_receitadialog);
        tvInfoDataSV = view.findViewById(R.id.tv_infodataservvenda_receitadialog);

        String data, valor, valorTotal, descon;
        valor = "R$ " + receita.getValor();
        valorTotal = "R$ " + receita.getValorTotal();
        descon = receita.getDesconto() + "%";


        tvCategoria.setText(receita.getCategoria().getNome());
        tvValorTotal.setText(valorTotal);
        tvValor.setText(valor);
        tvDescon.setText(descon);

        /*if (receita.getTipoReceita().equals("V")) {
            tvTipo.setText("Venda");
            data = DateFormat.getDateInstance(DateFormat.SHORT).format(receita.getDataVend());
            tvInfoDataSV.setText(data);
        } else {
            tvTipo.setText("Serviço");
            data = DateFormat.getDateInstance(DateFormat.SHORT).format(receita.getDataServ());
            tvDataSV.setText("Data do Serviço");
            tvInfoDataSV.setText(data);
        }
        if (receita.getDataPag() != null) {
            data = DateFormat.getDateInstance(DateFormat.SHORT).format(receita.getDataPag());
            tvDataPag.setText(data);
            if (receita.getFormaPagamento().equals("D")) {
                tvFormaPag.setText("Dinheiro");
            } else {
                tvFormaPag.setText("Cartão");
            }
        }
        if (receita.getDataVenc() != null) {
            data = DateFormat.getDateInstance(DateFormat.SHORT).format(receita.getDataVenc());
            tvDataVenc.setText(data);
        }*/

        return builder.create();
    }
}
