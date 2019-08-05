package com.example.jeconomy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.jeconomy.R;
import com.example.jeconomy.models.Categoria;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.MyViewHolder> {

    private List<Categoria> listCategoria;
    private LayoutInflater layoutInflater;
    private ControlCategoria controlCategoria;

    public void setControlCategoria(ControlCategoria controlCategoria){
        this.controlCategoria = controlCategoria;
    }

    public interface ControlCategoria {
        void deleteCategoria(Categoria categoria);
        void editCategoria(Categoria categoria);
    }

    public CategoriaAdapter(Context context, List<Categoria> listCategoria ) {
        this.listCategoria = listCategoria;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_categoria, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view, controlCategoria);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tvNome.setText(listCategoria.get(position).getNome());
    }

    @Override
    public int getItemCount() {
        return listCategoria.size();
    }

    public void addListItem(Categoria categoria, int position){
        listCategoria.add(categoria);
        notifyItemInserted(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tvNome;
        public ImageButton btnEdit, btnDelete;

        public MyViewHolder(@NonNull View itemView, ControlCategoria categoria) {
            super(itemView);

            tvNome = itemView.findViewById(R.id.tv_nome_itemcategoria);
            btnDelete = itemView.findViewById(R.id.btn_delete_itemcategoria);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        Categoria categoria = listCategoria.get(position);
                        controlCategoria.deleteCategoria(categoria);
                    }
                }
            });
        }
    }

}
