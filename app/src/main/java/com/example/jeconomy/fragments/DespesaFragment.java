package com.example.jeconomy.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jeconomy.PagamentoActivity;
import com.example.jeconomy.R;
import com.example.jeconomy.adapter.ContasAdapter;
import com.example.jeconomy.dialog.DespesaDialog;
import com.example.jeconomy.models.Despesa;
import com.example.jeconomy.models.Receita;
import com.orm.SugarContext;

import java.util.List;

public class DespesaFragment extends Fragment {
    private Spinner spTipo;
    private RecyclerView rvDespesa;
    private List<Despesa> listDespesa;
    private TextView tvConta;

    public DespesaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contas, container, false);

        spTipo = view.findViewById(R.id.sp_tipo_contas);
        rvDespesa = view.findViewById(R.id.rv_contas_contas);
        tvConta = view.findViewById(R.id.tv_conta_contas);

        tvConta.setText("Despesas");
        rvDespesa.setHasFixedSize(true);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(), R.array.tipo_conta,
                android.R.layout.simple_spinner_item);

        spTipo.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(RecyclerView.VERTICAL);
        rvDespesa.setLayoutManager(llm);

        spTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    updateRecycleView(1);
                } else {
                    updateRecycleView(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        return view;
    }

    private void updateRecycleView(int isPago) {
        try {
            SugarContext.init(getContext());
            listDespesa = Despesa.find(Despesa.class, "IS_PAGO = ?", "" + isPago);
            SugarContext.terminate();
            if (listDespesa != null) {
                ContasAdapter adapter = new ContasAdapter(getContext(), listDespesa, 'D');
                adapter.setControlConta(new ContasAdapter.ControlConta() {
                    @Override
                    public void openDespesaDialog(Despesa despesa) {
                        DespesaDialog dialog = new DespesaDialog(despesa);
                        dialog.show(getFragmentManager(), "Relatorio: Despesa");
                    }

                    @Override
                    public void openReceitaDialog(Receita receita) {
                    }
                });
                rvDespesa.setAdapter(adapter);
            }
        } catch (Exception e) {
            System.err.println("<=====================================>");
            e.printStackTrace();
            System.err.println("<=====================================>");
        }
    }
}
