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

import com.example.jeconomy.R;
import com.example.jeconomy.adapter.ContasAdapter;
import com.example.jeconomy.dialogs.ReceitaDialog;
import com.example.jeconomy.models.Despesa;
import com.example.jeconomy.models.Receita;
import com.example.jeconomy.models.Usuario;
import com.orm.SugarContext;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;


public class ReceitaFragment extends Fragment {
    private List<Receita> listReceita;
    private RecyclerView rvReceita;
    private Spinner spTipo;
    private Usuario user;

    public ReceitaFragment() {
        // Required empty public constructor
    }

    public ReceitaFragment(Usuario user, long id) {
        this.user = user;
        user.setId(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contas, container, false);

        rvReceita = view.findViewById(R.id.rv_contas_contas);
        spTipo = view.findViewById(R.id.sp_tipo_contas);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(), R.array.tipo_conta,
                android.R.layout.simple_spinner_item);
        spTipo.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(RecyclerView.VERTICAL);
        rvReceita.setLayoutManager(llm);

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
            listReceita = Select.from(Receita.class).where(Condition.prop("is_pago").eq(isPago),
                    Condition.prop("usuario").eq(user.getId())).list();
            if (listReceita != null) {
                ContasAdapter adapter = new ContasAdapter(getContext(), listReceita, 'R');
                adapter.setControlConta(new ContasAdapter.ControlConta() {
                    @Override
                    public void openDespesaDialog(Despesa despesa) {
                    }

                    @Override
                    public void openReceitaDialog(Receita receita) {
                        ReceitaDialog dialog = new ReceitaDialog(receita);
                        dialog.show(getFragmentManager(), "Relatorio: Receita");
                    }
                });
                rvReceita.setAdapter(adapter);
            }
        } catch (Exception e) {
            System.err.println("<=====================================>");
            e.printStackTrace();
            System.err.println("<=====================================>");
        }
    }

}
