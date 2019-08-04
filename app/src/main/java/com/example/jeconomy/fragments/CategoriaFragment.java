package com.example.jeconomy.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jeconomy.R;
import com.example.jeconomy.adapter.CategoriaAdapter;
import com.example.jeconomy.models.Categoria;

import java.util.ArrayList;
import java.util.List;

public class CategoriaFragment extends Fragment {

    private RecyclerView rvCategoria;
    private List<Categoria> listCategoria;

    public CategoriaFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categoria, container, false);

        rvCategoria = view.findViewById(R.id.rv_categoria_categoria);
        rvCategoria.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager llm = (LinearLayoutManager) rvCategoria.getLayoutManager();
                CategoriaAdapter categoriaAdapter = (CategoriaAdapter) rvCategoria.getAdapter();

                if(listCategoria.size() == llm.findLastCompletelyVisibleItemPosition() + 1){

                    /*for(int c = 0; c < 10; c++){
                        Categoria categoria = new Categoria("Categoria" + c);
                        categoriaAdapter.addListItem(categoria, listCategoria.size());
                    }*/
                }
            }
        });
        rvCategoria.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(RecyclerView.VERTICAL);
        rvCategoria.setLayoutManager(llm);

        try{
            listCategoria = (List<Categoria>) Categoria.findAll(Categoria.class);
        }
        catch (Exception e){
            System.err.println("<=====================================>");
            e.printStackTrace();
            System.err.println("<=====================================>");
            listCategoria = new ArrayList<Categoria>();
            listCategoria.add(new Categoria("nome"));
            Toast.makeText(getActivity(), "Nenhuma Categoria Cadastrada", Toast.LENGTH_SHORT).show();
        }

        CategoriaAdapter categoriaAdapter = new CategoriaAdapter(getActivity(), listCategoria);
        rvCategoria.setAdapter(categoriaAdapter);

        return view;
    }

}
