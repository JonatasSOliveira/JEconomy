package com.example.jeconomy.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jeconomy.R;
import com.example.jeconomy.RegisterDespesaActivity;
import com.example.jeconomy.models.Despesa;
import com.orm.SugarContext;

import java.util.List;


public class HomeFragment extends Fragment {

    private Button btnDespesa;
    private TextView tvValorDespesa, tvBalanço;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btnDespesa = view.findViewById(R.id.btn_despesa_home);
        tvValorDespesa = view.findViewById(R.id.tv_valordespesa_home);
        tvBalanço = view.findViewById(R.id.tv_valorbalanco_home);

        btnDespesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), RegisterDespesaActivity.class));
            }
        });

        atualizarValores();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        atualizarValores();
    }


    private void atualizarValores(){
        try {
            SugarContext.init(getContext());
            List<Despesa> listDespesa = Despesa.listAll(Despesa.class);
            SugarContext.terminate();

            double despesaTotal = 0;
            for (int c = 0; c < listDespesa.size(); c++) {
                if (listDespesa.get(c).isPago() == true) {
                    despesaTotal = despesaTotal + listDespesa.get(c).getValor();
                }
            }
            String aux = "R$: " + despesaTotal;
            tvValorDespesa.setText(aux);
            aux = "R$: " + (0 - despesaTotal);
            tvBalanço.setText(aux);

        } catch (Exception e) {
            System.err.println("<===========================================================>");
            e.printStackTrace();
            System.err.println("<===========================================================>");
        }
    }
}
