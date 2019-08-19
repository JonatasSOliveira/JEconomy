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
import com.example.jeconomy.adapter.DespesaAdapter;
import com.example.jeconomy.dialog.DespesaDialog;
import com.example.jeconomy.models.Despesa;
import com.orm.SugarContext;

import java.util.List;

public class DespesaFragment extends Fragment {
    private Spinner spTipo;
    private RecyclerView rvDespesa;
    private List<Despesa> listDespesa;

    public DespesaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_despesa, container, false);

        spTipo = view.findViewById(R.id.sp_tipo_despesa);
        rvDespesa = view.findViewById(R.id.rv_despesa_despesa);
        rvDespesa.setHasFixedSize(true);

        String tipo[] = {"PAGAS", "À PAGAR"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,
                tipo);

        spTipo.setAdapter(arrayAdapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(RecyclerView.VERTICAL);
        rvDespesa.setLayoutManager(llm);

        spTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    updateRecycleView(1);
                }
                else{
                    updateRecycleView(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });



        return view;
    }

    private void updateRecycleView(int isPago){
        try {
            SugarContext.init(getContext());
            listDespesa = Despesa.find(Despesa.class, "IS_PAGO = ?", "" + isPago);
            SugarContext.terminate();
            if (listDespesa != null) {
                DespesaAdapter adapter = new DespesaAdapter(getContext(), listDespesa);
                adapter.setControlDespesa(new DespesaAdapter.ControlDespesa() {
                    @Override
                    public void openDialog(Despesa despesa) {
                        DespesaDialog dialog = new DespesaDialog(despesa);
                        dialog.show(getFragmentManager(), "Relatorio: Despesa");
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
