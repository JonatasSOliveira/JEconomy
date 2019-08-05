package com.example.jeconomy.fragments;

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
                openDialog();
            }
        });

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
        SugarContext.init(getContext());
        updateRecycleView();
        SugarContext.init(getContext());
        return view;
    }

    public void openDialog(){
        CategoriaDialog categoriaDialog = new CategoriaDialog();
        categoriaDialog.setTargetFragment(CategoriaFragment.this, 1);
        categoriaDialog.show(getFragmentManager(), "Cadastro: Categoria");
    }

    @Override
    public void sendInput(String input) {
        try {
            SugarContext.init(getContext());
            Categoria categoria = new Categoria(input);
            categoria.save();

        }
        catch (Exception e){
            System.err.println("<=====================================>");
            e.printStackTrace();
            System.err.println("<=====================================>");
        }finally {
            updateRecycleView();
            SugarContext.terminate();
        }

    }

    private void updateRecycleView(){
        try{
            List<Categoria> aux = new ArrayList<>();
            for(int c = 0; c < 10; c++){
                Categoria categoria = Categoria.findById(Categoria.class, c);
                if(categoria != null ){
                    aux.add(categoria);
                }
            }
            listCategoria = aux;
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
                Toast.makeText(getContext(), categoria.getNome(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
