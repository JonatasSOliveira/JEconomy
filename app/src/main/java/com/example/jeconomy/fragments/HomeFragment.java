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
import com.example.jeconomy.RegisterReceitaActivity;
import com.example.jeconomy.models.Despesa;
import com.example.jeconomy.models.Receita;
import com.example.jeconomy.models.Usuario;
import com.orm.SugarContext;

import java.util.List;


public class HomeFragment extends Fragment {

    private Button btnDespesa, btnReceita;
    private TextView tvValorDespesa, tvBalanço, tvValorReceita;
    private Usuario usuario;

    public HomeFragment() {
        // Required empty public constructor
    }

    public HomeFragment(Usuario usuario){
        this.usuario = usuario;
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btnDespesa = view.findViewById(R.id.btn_despesa_home);
        btnReceita = view.findViewById(R.id.btn_receita_home);
        tvValorDespesa = view.findViewById(R.id.tv_valordespesa_home);
        tvValorReceita = view.findViewById(R.id.tv_valorreceita_home);
        tvBalanço = view.findViewById(R.id.tv_valorbalanco_home);

        btnDespesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), RegisterDespesaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", usuario);
                intent.putExtra("home", bundle);
                startActivity(intent);
            }
        });

        btnReceita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), RegisterReceitaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", usuario);
                intent.putExtra("home", bundle);
                startActivity(intent);
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
        double despesaTotal = 0;
        double receitaTotal = 0;
        try {
            SugarContext.init(getContext());
            List<Despesa> listDespesa = Despesa.listAll(Despesa.class);
            SugarContext.terminate();

            for (int c = 0; c < listDespesa.size(); c++) {
                if (listDespesa.get(c).isPago() == true) {
                    despesaTotal = despesaTotal + listDespesa.get(c).getValor();
                }
            }
            String aux = "R$: " + despesaTotal;
            tvValorDespesa.setText(aux);

        } catch (Exception e) {
            System.err.println("<===========================================================>");
            e.printStackTrace();
            System.err.println("<===========================================================>");
        }
        try {
            SugarContext.init(getContext());
            List<Receita> listReceita = Receita.listAll(Receita.class);
            SugarContext.terminate();

            for (int c = 0; c < listReceita.size(); c++) {
                if (listReceita.get(c).isPago() == true) {
                    receitaTotal = receitaTotal + listReceita.get(c).getValorTotal();
                }
            }
            String aux = "R$: " + receitaTotal;
            tvValorReceita.setText(aux);

        } catch (Exception e) {
            System.err.println("<===========================================================>");
            e.printStackTrace();
            System.err.println("<===========================================================>");
        }
        String aux = "R$: " + (receitaTotal - despesaTotal);
        tvBalanço.setText(aux);
    }
}
