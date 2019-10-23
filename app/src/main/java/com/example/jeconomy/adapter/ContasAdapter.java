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
import com.example.jeconomy.models.Receita;

import java.util.List;

public class ContasAdapter extends RecyclerView.Adapter<ContasAdapter.MyViewHolder> {

    private List<Despesa> listDespesa;
    private List<Receita> listReceita;
    private LayoutInflater layoutInflater;
    private ControlConta controlConta;

    public interface ControlConta {
        void openDespesaDialog(Despesa despesa);

        void openReceitaDialog(Receita receita);
    }

    public void setControlConta(ControlConta controlConta) {
        this.controlConta = controlConta;
    }

    public ContasAdapter(Context context, List list, char verif) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (verif == 'D') {
            this.listDespesa = list;
        } else {
            this.listReceita = list;
        }

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
        if (listDespesa != null) {
            holder.tvCategoria.setText(listDespesa.get(position).getCategoria().getNome());
            String date;
            if (listDespesa.get(position).isPago()) {
                //date = DateFormat.getDateInstance(DateFormat.SHORT).format(listDespesa.get(position).getDataPag());
                //holder.tvData.setText(date);
            } else {
                //date = DateFormat.getDateInstance(DateFormat.SHORT).format(listDespesa.get(position).getDataVenc());
                //holder.tvData.setText(date);
            }
            holder.btnVisualizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    controlConta.openDespesaDialog(listDespesa.get(position));
                }
            });
        } else {
            holder.tvCategoria.setText(listReceita.get(position).getCategoria().getNome());
            String date;
            if (listReceita.get(position).isPago()) {
                //date = DateFormat.getDateInstance(DateFormat.SHORT).format(listReceita.get(position).getDataPag());
                //holder.tvData.setText(date);
            } else {
                //date = DateFormat.getDateInstance(DateFormat.SHORT).format(listReceita.get(position).getDataVenc());
                //holder.tvData.setText(date);
            }
            holder.btnVisualizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    controlConta.openReceitaDialog(listReceita.get(position));
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (listDespesa != null) {
            return listDespesa.size();
        }
        return listReceita.size();
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
