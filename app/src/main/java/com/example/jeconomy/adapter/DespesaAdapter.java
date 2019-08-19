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
import com.example.jeconomy.models.Despesa;

import java.text.DateFormat;
import java.util.List;

public class DespesaAdapter extends RecyclerView.Adapter<DespesaAdapter.MyViewHolder> {

    private List<Despesa> listDespesa;
    private LayoutInflater layoutInflater;
    private ControlDespesa controlDespesa;

    public interface ControlDespesa {
        void openDialog(Despesa despesa);
    }

    public void setControlDespesa(ControlDespesa controlDespesa){
        this.controlDespesa = controlDespesa;
    }


    public DespesaAdapter(Context context, List<Despesa> listDespesa) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listDespesa = listDespesa;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_despesa, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tvCategoria.setText(listDespesa.get(position).getCategoria().getNome());
        String date;
        if(listDespesa.get(position).isPago()){
            date = DateFormat.getDateInstance(DateFormat.SHORT).format(listDespesa.get(position).getDataPag());
            holder.tvData.setText(date);
        }
        else{
            date = DateFormat.getDateInstance(DateFormat.SHORT).format(listDespesa.get(position).getDataVenc());
            holder.tvData.setText(date);
        }
        holder.btnVisualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controlDespesa.openDialog(listDespesa.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDespesa.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCategoria, tvData;
        public ImageButton btnVisualizar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCategoria = itemView.findViewById(R.id.tv_categoria_itemdespesa);
            tvData = itemView.findViewById(R.id.tv_data_itemdespesa);
            btnVisualizar = itemView.findViewById(R.id.btn_visualizar_itemdespesa);

        }
    }


}
