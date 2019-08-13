package com.example.jeconomy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeconomy.dialog.DatePickerFragment;
import com.example.jeconomy.models.Categoria;
import com.example.jeconomy.models.Despesa;
import com.google.android.material.textfield.TextInputLayout;
import com.orm.SugarContext;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RegisterDespesaActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Spinner spCategoria, spFormaPag, spTipoDespesa;
    private List<Categoria> listCategoria;
    private Button btnData, btnCadastrar;
    private TextInputLayout tilData, tilValor;
    private Date date;
    private TextView tvFormaPag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_despesa);

        spCategoria = findViewById(R.id.sp_categoria_registerdespesa);
        spFormaPag = findViewById(R.id.sp_formapag_despesa);
        spTipoDespesa = findViewById(R.id.sp_tipo_despesa);
        btnCadastrar = findViewById(R.id.btn_cadastrar_despesa);
        btnData = findViewById(R.id.btn_data_despesa);
        tilData = findViewById(R.id.til_data_despesa);
        tilValor = findViewById(R.id.til_valor_despesa);
        tvFormaPag = findViewById(R.id.tv_formapag_despesa);

        tilData.getEditText().setEnabled(false);
        tilData.setHint("DATA DE PAGAMENTO");

        String[] listNome, listFormaPag = {"ESCOLHA", "DINHEIRO", "CARTÃO"}, listTipo = {"PAGA", "À PAGAR"};

        try {
            SugarContext.init(RegisterDespesaActivity.this);
            listCategoria = Categoria.listAll(Categoria.class);
            SugarContext.terminate();
            listNome = new String[listCategoria.size() + 1];
            listNome[0] = "ESCOLHA";

            for (int c = 0; c < listCategoria.size(); c++) {
                listNome[c + 1] = listCategoria.get(c).getNome();
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(RegisterDespesaActivity.this,
                    android.R.layout.simple_spinner_item, listNome);
            spCategoria.setAdapter(arrayAdapter);
            arrayAdapter = new ArrayAdapter<>(RegisterDespesaActivity.this,
                    android.R.layout.simple_spinner_item, listFormaPag);
            spFormaPag.setAdapter(arrayAdapter);
            arrayAdapter = new ArrayAdapter<>(RegisterDespesaActivity.this,
                    android.R.layout.simple_spinner_item, listTipo);
            spTipoDespesa.setAdapter(arrayAdapter);

            spTipoDespesa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == 0) {
                        tilData.setHint("DATA DE PAGAMENTO");
                        spFormaPag.setVisibility(View.VISIBLE);
                        tvFormaPag.setVisibility(View.VISIBLE);
                        spFormaPag.setEnabled(true);
                    } else {
                        tilData.setHint("DATA DE VENCIMENTO");
                        spFormaPag.setVisibility(View.INVISIBLE);
                        tvFormaPag.setVisibility(View.INVISIBLE);
                        spFormaPag.setEnabled(false);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            btnData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment datePicker = new DatePickerFragment();
                    datePicker.show(getSupportFragmentManager(), "date picker");
                }
            });

            btnCadastrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int categoriaItem = spCategoria.getSelectedItemPosition();
                    int formaPagItem = spFormaPag.getSelectedItemPosition();
                    int tipoDespesa = spTipoDespesa.getSelectedItemPosition();
                    String auxValor = tilValor.getEditText().getText().toString();
                    String data = tilData.getEditText().getText().toString();

                    if ((tipoDespesa == 0 && (auxValor.isEmpty() || categoriaItem == 0 || formaPagItem == 0 || data.isEmpty()
                    )) || (tipoDespesa == 1 && (auxValor.isEmpty() || categoriaItem == 0 || data.isEmpty()))) {
                        Toast.makeText(RegisterDespesaActivity.this, "Preencha todos os Campos",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        double valor = Double.parseDouble(auxValor);
                        Categoria categoria = listCategoria.get(categoriaItem - 1);
                        Despesa despesa;

                        if (spTipoDespesa.getSelectedItemPosition() == 0) {
                            char formaPag;
                            if (formaPagItem == 1) {
                                formaPag = 'D';
                            } else {
                                formaPag = 'C';
                            }
                            despesa = new Despesa(true, formaPag, date, null, valor, categoria);
                        } else {
                            despesa = new Despesa(false, '0', null, date, valor, categoria);
                        }
                        try {
                            SugarContext.init(RegisterDespesaActivity.this);
                            despesa.save();
                            SugarContext.terminate();
                            Toast.makeText(RegisterDespesaActivity.this, "SALVO COM SUCESSO", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            System.err.println("<===========================================================>");
                            e.printStackTrace();
                            System.err.println("<===========================================================>");
                            Toast.makeText(RegisterDespesaActivity.this, "UM ERRO OCORREU", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });


        } catch (Exception e) {
            System.err.println("<===========================================================>");
            e.printStackTrace();
            System.err.println("<===========================================================>");
        }


    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        String dateSelected = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
        tilData.getEditText().setText(dateSelected);
        date = calendar.getTime();
    }
}
