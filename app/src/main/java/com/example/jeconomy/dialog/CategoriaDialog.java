package com.example.jeconomy.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.jeconomy.R;
import com.google.android.material.textfield.TextInputLayout;

public class CategoriaDialog extends AppCompatDialogFragment {
    private TextInputLayout tilCategoria;
    public interface OnInputSelected{
        void sendInput(String input);
    }
    public OnInputSelected onInputSelected;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_cadastro_categoria, null);
        builder.setView(view).setTitle("Nova Categoria").setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String categoria = tilCategoria.getEditText().getText().toString().trim().toUpperCase();
                if(!categoria.isEmpty()){
                    onInputSelected.sendInput(categoria);
                }
            }
        });

        tilCategoria = view.findViewById(R.id.til_categoria_dialogcategoria);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            onInputSelected = (OnInputSelected) getTargetFragment();
        }
        catch (ClassCastException e){
            e.printStackTrace();
        }
    }
}
