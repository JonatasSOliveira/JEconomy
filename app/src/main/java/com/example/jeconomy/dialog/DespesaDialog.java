package com.example.jeconomy.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.jeconomy.R;
import com.example.jeconomy.models.Despesa;

public class DespesaDialog extends AppCompatDialogFragment {
    private TextView tvCategoria;
    private Despesa despesa;


    public DespesaDialog(Despesa despesa) {
        this.despesa = despesa;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_despesa, null);

        builder.setView(view).setTitle("Despesa").setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        tvCategoria = view.findViewById(R.id.tv_infocategoria_despesadialog);
        tvCategoria.setText(despesa.getCategoria().getNome());

        return builder.create();
    }

}
