package com.example.jeconomy.fragments;

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

import com.example.jeconomy.R;
import com.example.jeconomy.adapter.ContasAdapter;
import com.example.jeconomy.dialogs.DespesaDialog;
import com.example.jeconomy.models.Despesa;
import com.example.jeconomy.models.Receita;
import com.example.jeconomy.models.Usuario;
import com.orm.SugarContext;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

public class DespesaFragment extends Fragment {
    private Spinner spTipo;
    private RecyclerView rvDespesa;
    private List<Despesa> listDespesa;
    private TextView tvConta;
    private Usuario user;

    public DespesaFragment() {
        // Required empty public constructor
    }

    public DespesaFragment(Usuario user, long id){
        this.user = user;
        user.setId(id);
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
            String order;
            if (isPago == 1) {
                order = "data_pag Desc";
            } else {
                order = "data_venc Desc";
            }

            SugarContext.init(getContext());

            listDespesa = Select.from(Despesa.class).where(Condition.prop("is_pago").eq(isPago),
                    Condition.prop("usuario").eq(user.getId())).orderBy(order).list();
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
