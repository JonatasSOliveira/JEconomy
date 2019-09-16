package com.example.jeconomy.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.jeconomy.R;
import com.example.jeconomy.models.Categoria;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterCategoriaDialog extends AppCompatDialogFragment {
    private TextInputLayout tilCategoria;
    private Categoria oldCategoria;

    public interface OnInputSelected {
        void sendInput(String input, Categoria oldCategoria);
    }

    public OnInputSelected onInputSelected;

    public RegisterCategoriaDialog() {
        oldCategoria = null;
    }

    public RegisterCategoriaDialog(Categoria oldCategoria) {
        this.oldCategoria = oldCategoria;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_register_categoria, null);
        builder.setView(view).setTitle("Nova Categoria").setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String nome = tilCategoria.getEditText().getText().toString().trim().toUpperCase();
                if (!nome.isEmpty()) {
                    onInputSelected.sendInput(nome, oldCategoria);
                }
            }
        });

        tilCategoria = view.findViewById(R.id.til_categoria_dialogcategoria);
        if (oldCategoria != null) {
            tilCategoria.getEditText().setText(oldCategoria.getNome());
        }

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            if (getTargetFragment() != null) {
                onInputSelected = (OnInputSelected) getTargetFragment();
            } else {
                onInputSelected = (OnInputSelected) getActivity();
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}