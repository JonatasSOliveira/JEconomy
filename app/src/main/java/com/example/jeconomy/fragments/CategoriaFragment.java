package com.example.jeconomy.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.example.jeconomy.R;
import com.example.jeconomy.adapter.CategoriaAdapter;
import com.example.jeconomy.dialog.CategoriaDialog;
import com.example.jeconomy.models.Categoria;
import com.orm.SugarContext;
import java.util.ArrayList;
import java.util.List;

public class CategoriaFragment extends Fragment implements CategoriaDialog.OnInputSelected {

    private RecyclerView rvCategoria;
    private List<Categoria> listCategoria;
    private Button btnCadastrar;

    public CategoriaFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categoria, container, false);

        btnCadastrar = view.findViewById(R.id.btn_cadastrar_categoria);
        rvCategoria = view.findViewById(R.id.rv_categoria_categoria);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(null);
            }
        });

        rvCategoria.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(RecyclerView.VERTICAL);
        rvCategoria.setLayoutManager(llm);
        updateRecycleView();
        return view;
    }

    public void openDialog(Categoria categoria){
        CategoriaDialog categoriaDialog;
        if(categoria == null){
            categoriaDialog = new CategoriaDialog();
        }
        else{
            categoriaDialog = new CategoriaDialog(categoria);
        }
        categoriaDialog.setTargetFragment(CategoriaFragment.this, 1);
        categoriaDialog.show(getFragmentManager(), "Cadastro: Categoria");
    }

    @Override
    public void sendInput(String input, Categoria categoria) {
        try {
            SugarContext.init(getContext());
            if(categoria == null){
                categoria = new Categoria(input);
                categoria.save();
            }
            else{
                categoria.setNome(input);
                categoria.save();
            }
        }
        catch (Exception e){
            System.err.println("<=====================================>");
            e.printStackTrace();
            System.err.println("<=====================================>");
        }finally {
            SugarContext.terminate();
            updateRecycleView();
        }

    }

    private void updateRecycleView(){
        try{
            SugarContext.init(getContext());
            listCategoria = Categoria.listAll(Categoria.class);
            SugarContext.terminate();
            if(listCategoria != null){
                CategoriaAdapter categoriaAdapter = new CategoriaAdapter(getActivity(), listCategoria);
                rvCategoria.setAdapter(categoriaAdapter);
                categoriaAdapter.setControlCategoria(new CategoriaAdapter.ControlCategoria() {
                    @Override
                    public void deleteCategoria(Categoria categoria) {
                        try{
                            SugarContext.init(getContext());
                            categoria.delete();
                            updateRecycleView();
                            SugarContext.terminate();
                        }
                        catch (Exception e){
                            System.err.println("<=====================================>");
                            e.printStackTrace();
                            System.err.println("<=====================================>");
                        }
                    }
                    @Override
                    public void editCategoria(Categoria categoria) {
                        openDialog(categoria);
                    }
                });
            }
        }
        catch (Exception e){
            System.err.println("<=====================================>");
            e.printStackTrace();
            System.err.println("<=====================================>");
            Toast.makeText(getActivity(), "Algum erro ocorreu", Toast.LENGTH_SHORT).show();
        }
    }
}
