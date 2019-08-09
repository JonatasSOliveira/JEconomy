package com.example.jeconomy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.jeconomy.models.Categoria;
import com.orm.SugarContext;

import java.util.List;

public class RegisterDespesaActivity extends AppCompatActivity {

    private Spinner spinner;
    private List<Categoria> listCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_despesa);

        try {
            SugarContext.init(RegisterDespesaActivity.this);
            listCategoria = Categoria.listAll(Categoria.class);
            SugarContext.terminate();
            spinner = findViewById(R.id.sp_categoria_registerdespesa);
            String[] listNome = new String[listCategoria.size() + 1];
            listNome[0] = "Escolha a Categoria";
            for (int c = 0; c < listCategoria.size(); c++){
                listNome[c + 1] = listCategoria.get(c).getNome();
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(RegisterDespesaActivity.this, android.R.layout.simple_spinner_item, listNome);
            spinner.setAdapter(arrayAdapter);

        } catch (Exception e) {
            System.err.println("<===========================================================>");
            e.printStackTrace();
            System.err.println("<===========================================================>");
        }


    }
}
