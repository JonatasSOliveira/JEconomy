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
import com.example.jeconomy.dialog.RegisterCategoriaDialog;
import com.example.jeconomy.models.Categoria;
import com.example.jeconomy.models.Despesa;
import com.example.jeconomy.models.Usuario;
import com.google.android.material.textfield.TextInputLayout;
import com.orm.SugarContext;
import com.orm.query.Select;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RegisterDespesaActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, RegisterCategoriaDialog.OnInputSelected {

    private Spinner spCategoria, spFormaPag, spTipoDespesa;
    private List<Categoria> listCategoria;
    private Button btnData, btnCadastrar;
    private TextInputLayout tilData, tilValor;
    private Date date;
    private TextView tvFormaPag;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_despesa);

        spCategoria = findViewById(R.id.sp_categoria_registerdespesa);
        spFormaPag = findViewById(R.id.sp_formapag_registerdespesa);
        spTipoDespesa = findViewById(R.id.sp_tipo_registerdespesa);
        btnCadastrar = findViewById(R.id.btn_cadastrar_registerdespesa);
        btnData = findViewById(R.id.btn_data_registerdespesa);
        tilData = findViewById(R.id.til_data_registerdespesa);
        tilValor = findViewById(R.id.til_valor_registerdespesa);
        tvFormaPag = findViewById(R.id.tv_formapag_registerdespesa);

        tilData.getEditText().setEnabled(false);
        tilData.setHint("DATA DE PAGAMENTO");

        Bundle bundle = getIntent().getBundleExtra("home");
        usuario = (Usuario) bundle.getSerializable("user");

        String[] listFormaPag = {"ESCOLHA", "DINHEIRO", "CARTÃO"}, listTipo = {"PAGA", "À PAGAR"};

        try {
            updateCategoria();

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(RegisterDespesaActivity.this,
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

            spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == 1) {
                        RegisterCategoriaDialog dialog = new RegisterCategoriaDialog();
                        dialog.show(getSupportFragmentManager(), "Cadastro Categoria");
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

                    if (categoriaItem == 0 || auxValor.isEmpty() || data.isEmpty() ||
                            (formaPagItem == 0 && tipoDespesa == 0)) {
                        Toast.makeText(RegisterDespesaActivity.this, "Preencha todos os Campos",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        double valor = Double.parseDouble(auxValor);
                        Categoria categoria = listCategoria.get(categoriaItem - 2);
                        Despesa despesa = new Despesa(valor, categoria, usuario);

                        if (spTipoDespesa.getSelectedItemPosition() == 0) {
                            if (formaPagItem == 1) {
                                despesa.setFormaPag("D");
                            } else {
                                despesa.setFormaPag("C");
                            }
                            despesa.setPago(true);
                            despesa.setDataPag(date);

                        } else {
                            despesa.setPago(false);
                            despesa.setDataVenc(date);
                        }

                        salvar(despesa);

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

        Calendar hoje = Calendar.getInstance();
        Calendar dateSelected = Calendar.getInstance();
        dateSelected.set(Calendar.YEAR, year);
        dateSelected.set(Calendar.MONTH, month);
        dateSelected.set(Calendar.DAY_OF_MONTH, day);

        if (spTipoDespesa.getSelectedItemPosition() == 1) {
            if (dateSelected.get(Calendar.YEAR) < hoje.get(Calendar.YEAR)
                    || dateSelected.get(Calendar.MONTH) < hoje.get(Calendar.MONTH)
                    || dateSelected.get(Calendar.DAY_OF_MONTH) <= hoje.get(Calendar.DAY_OF_MONTH)) {
                Toast.makeText(this, "Selecione uma data posterior ao dia atual", Toast.LENGTH_SHORT).show();
            } else {
                setDate(dateSelected);
            }
        } else {
            if (dateSelected.get(Calendar.YEAR) > hoje.get(Calendar.YEAR)
                    || dateSelected.get(Calendar.MONTH) > hoje.get(Calendar.MONTH)
                    || dateSelected.get(Calendar.DAY_OF_MONTH) > hoje.get(Calendar.DAY_OF_MONTH)) {
                Toast.makeText(this, "Selecione o dia atual ou anterior", Toast.LENGTH_SHORT).show();
            } else {
                setDate(dateSelected);
            }
        }
    }

    private void setDate(Calendar dateSelected){
        String date = DateFormat.getDateInstance(DateFormat.SHORT).format(dateSelected.getTime());
        tilData.getEditText().setText(date);
        this.date = dateSelected.getTime();
    }


    private void limparCampos() {
        spCategoria.setSelection(0);
        spFormaPag.setSelection(0);
        spTipoDespesa.setSelection(0);
        tilData.getEditText().setText("");
        tilValor.getEditText().setText("");
    }

    private void updateCategoria() {
        String[] listNome;

        SugarContext.init(RegisterDespesaActivity.this);
        listCategoria = Select.from(Categoria.class).orderBy("nome").list();
        SugarContext.terminate();
        listNome = new String[listCategoria.size() + 2];
        listNome[0] = "ESCOLHA";
        listNome[1] = "NOVA CATEGORIA";

        for (int c = 0; c < listCategoria.size(); c++) {
            listNome[c + 2] = listCategoria.get(c).getNome();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(RegisterDespesaActivity.this,
                android.R.layout.simple_spinner_item, listNome);
        spCategoria.setAdapter(arrayAdapter);
    }

    @Override
    public void sendInput(String input, Categoria categoria) {
        try {
            SugarContext.init(RegisterDespesaActivity.this);
            categoria = new Categoria(input);
            categoria.save();

        } catch (Exception e) {
            System.err.println("<=====================================>");
            e.printStackTrace();
            System.err.println("<=====================================>");
        } finally {
            SugarContext.terminate();
            updateCategoria();
        }
    }

    private void salvar(Despesa despesa) {
        try {
            SugarContext.init(RegisterDespesaActivity.this);
            despesa.save();
            SugarContext.terminate();
            limparCampos();
            Toast.makeText(RegisterDespesaActivity.this, "SALVO COM SUCESSO", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            System.err.println("<===========================================================>");
            e.printStackTrace();
            System.err.println("<===========================================================>");
            Toast.makeText(RegisterDespesaActivity.this, "UM ERRO OCORREU", Toast.LENGTH_SHORT).show();
        }
    }
}
