package com.example.jeconomy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
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
import com.example.jeconomy.models.Receita;
import com.example.jeconomy.models.Usuario;
import com.google.android.material.textfield.TextInputLayout;
import com.orm.SugarContext;
import com.orm.query.Select;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RegisterReceitaActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, RegisterCategoriaDialog.OnInputSelected {

    private Spinner spCategoria, spTipoPag, spTipo, spFormaPag;
    private List<Categoria> listCategoria;
    private TextInputLayout tilDataPv, tilDataSv, tilValor, tilDescon;
    private TextView tvFormaPag, tvValorTotal;
    private Button btnDataPv, btnDataSv, btnCadastrar;
    private Usuario usuario;
    private Date dataPv, dataSv;
    private byte dateOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_receita);

        spCategoria = findViewById(R.id.sp_categoria_registerreceita);
        spTipoPag = findViewById(R.id.sp_tipopag_registerreceita);
        spTipo = findViewById(R.id.sp_tipo_registerreceita);
        spFormaPag = findViewById(R.id.sp_formapag_registerreceita);
        tilDataPv = findViewById(R.id.til_datapv_registerreceita);
        tilDataSv = findViewById(R.id.til_datasv_registerreceita);
        tilValor = findViewById(R.id.til_valor_registerreceita);
        tilDescon = findViewById(R.id.til_desconto_registerreceita);
        tvFormaPag = findViewById(R.id.tv_formapag_registerreceita);
        tvValorTotal = findViewById(R.id.tv_valortotal_registerreceita);
        btnDataPv = findViewById(R.id.btn_datapv_registerreceita);
        btnDataSv = findViewById(R.id.btn_datasv_registerreceita);
        btnCadastrar = findViewById(R.id.btn_cadastrar_registerreceita);

        Bundle bundle = getIntent().getBundleExtra("home");
        usuario = (Usuario) bundle.getSerializable("user");

        String[] arrTipoPag = {"PAGA", "À PAGAR"}, arrTipo = {"VENDA", "SERVIÇO"};
        String[] arrFormaPag = {"ESCOLHA", "DINHEIRO", "CARTÃO"};

        tilDataPv.setHint("DATA DE PAGAMENTO");
        tilDataPv.setEnabled(false);
        tilDataSv.setHint("DATA DA VENDA");
        tilDataSv.setEnabled(false);
        tilDescon.getEditText().setText("0");

        try {
            updateCategoria();

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(RegisterReceitaActivity.this,
                    android.R.layout.simple_spinner_item, arrTipoPag);
            spTipoPag.setAdapter(arrayAdapter);

            arrayAdapter = new ArrayAdapter<>(RegisterReceitaActivity.this,
                    android.R.layout.simple_spinner_item, arrTipo);
            spTipo.setAdapter(arrayAdapter);

            arrayAdapter = new ArrayAdapter<>(RegisterReceitaActivity.this,
                    android.R.layout.simple_spinner_item, arrFormaPag);
            spFormaPag.setAdapter(arrayAdapter);

            spTipoPag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == 0) {
                        tilDataPv.setHint("DATA DE PAGAMENTO");
                        tvFormaPag.setVisibility(View.VISIBLE);
                        spFormaPag.setVisibility(View.VISIBLE);
                        spFormaPag.setEnabled(true);
                    } else {
                        tilDataPv.setHint("DATA DE VENCIMENTO");
                        tvFormaPag.setVisibility(View.INVISIBLE);
                        spFormaPag.setVisibility(View.INVISIBLE);
                        spFormaPag.setEnabled(false);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            spTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == 0) {
                        tilDataSv.setHint("DATA DA VENDA");
                    } else {
                        tilDataSv.setHint("DATA DO SERVIÇO");
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            btnDataPv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dateOption = 0;
                    DatePickerFragment datePicker = new DatePickerFragment();
                    datePicker.show(getSupportFragmentManager(), "date picker");
                }
            });

            btnDataSv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dateOption = 1;
                    DatePickerFragment datePicker = new DatePickerFragment();
                    datePicker.show(getSupportFragmentManager(), "date picker");
                }
            });

            tilValor.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    updateValor();
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });

            tilDescon.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    updateValor();
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });

            tilValor.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    return false;
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

            btnCadastrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int auxCategoria = spCategoria.getSelectedItemPosition();
                    int tipoPag = spTipoPag.getSelectedItemPosition();
                    String etDataPv = tilDataPv.getEditText().getText().toString();
                    String etDataSv = tilDataSv.getEditText().getText().toString();
                    String auxValor = tilValor.getEditText().getText().toString();
                    String auxDescon = tilDescon.getEditText().getText().toString();
                    int formaPag = spFormaPag.getSelectedItemPosition();

                    if (auxCategoria == 0 || etDataPv.isEmpty() || etDataSv.isEmpty() || auxValor.isEmpty()
                            || auxDescon.isEmpty() || (tipoPag == 0 && formaPag == 0)) {
                        Toast.makeText(RegisterReceitaActivity.this, "Preencha Todos os Campos",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Categoria categoria = listCategoria.get(auxCategoria - 2);

                        if(!categoria.isUsed()){
                            categoria.setUsed(true);
                            salvar(categoria);
                        }

                        double valor = Double.parseDouble(auxValor);
                        double descon = Double.parseDouble(auxDescon);
                        double valorTotal = valor - (valor * descon / 100);

                        Receita receita = new Receita(categoria, valor, descon, valorTotal, usuario);

                        int tipo = spTipo.getSelectedItemPosition();

                        if (tipo == 0) {
                            receita.setTipoReceita("V");
                            receita.setDataVend(dataSv);

                        } else {
                            receita.setTipoReceita("S");
                            receita.setDataServ(dataSv);
                        }
                        if (tipoPag == 0) {
                            receita.setPago(true);
                            receita.setDataPag(dataPv);
                            if (formaPag == 1) {
                                receita.setFormaPagamento("D");
                            } else {
                                receita.setFormaPagamento("C");
                            }
                        } else {
                            receita.setPago(false);
                            receita.setDataVenc(dataPv);
                        }

                        save(receita);

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

        if (spTipoPag.getSelectedItemPosition() == 1 || spTipo.getSelectedItemPosition() == 0) {
            if (dateSelected.get(Calendar.YEAR) < hoje.get(Calendar.YEAR)
                    || dateSelected.get(Calendar.MONTH) < hoje.get(Calendar.MONTH)
                    || dateSelected.get(Calendar.DAY_OF_MONTH) <= hoje.get(Calendar.DAY_OF_MONTH)) {
                Toast.makeText(this, "Selecione uma data de vencimento posterior ao dia atual",
                        Toast.LENGTH_SHORT).show();
            } else {
                setDate(dateSelected, dateOption);
            }
        } else {
            if (dateSelected.get(Calendar.YEAR) > hoje.get(Calendar.YEAR)
                    || dateSelected.get(Calendar.MONTH) > hoje.get(Calendar.MONTH)
                    || (dateSelected.get(Calendar.MONTH) == hoje.get(Calendar.MONTH) &&
                    dateSelected.get(Calendar.DAY_OF_MONTH) > hoje.get(Calendar.DAY_OF_MONTH))) {
                Toast.makeText(this, "Selecione uma data de pagamento atual ou anterior",
                        Toast.LENGTH_SHORT).show();
            } else {
                setDate(dateSelected, dateOption);
            }
        }
        if (spTipo.getSelectedItemPosition() == 1) {
            setDate(dateSelected, dateOption);
        }

    }

    private void setDate(Calendar dateSelected, int dateOption) {
        String data = DateFormat.getDateInstance(DateFormat.SHORT).format(dateSelected.getTime());
        tilDataPv.getEditText().setText(data);
        if (dateOption == 0) {
            dataPv = dateSelected.getTime();
        } else {
            dataSv = dateSelected.getTime();
        }

    }


    private void limparCampos() {
        String tv = "Valor Total: R$ 0,00";
        spCategoria.setSelection(0);
        spTipoPag.setSelection(0);
        spTipo.setSelection(0);
        spFormaPag.setSelection(0);
        tilDataPv.getEditText().setText("");
        tilDataSv.getEditText().setText("");
        tilValor.getEditText().setText("");
        tilDescon.getEditText().setText("");
        tvValorTotal.setText(tv);
    }

    private void updateCategoria() {
        String[] listNome;

        SugarContext.init(RegisterReceitaActivity.this);
        listCategoria = Select.from(Categoria.class).orderBy("nome").list();
        SugarContext.terminate();
        listNome = new String[listCategoria.size() + 2];
        listNome[0] = "ESCOLHA";
        listNome[1] = "NOVA CATEGORIA";

        for (int c = 0; c < listCategoria.size(); c++) {
            listNome[c + 2] = listCategoria.get(c).getNome();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(RegisterReceitaActivity.this,
                android.R.layout.simple_spinner_item, listNome);
        spCategoria.setAdapter(arrayAdapter);
    }

    @Override
    public void sendInput(String input, Categoria categoria) {
        try {
            SugarContext.init(RegisterReceitaActivity.this);
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

    private void save(Receita receita) {
        try {
            SugarContext.init(RegisterReceitaActivity.this);
            receita.save();
            SugarContext.terminate();
            Toast.makeText(RegisterReceitaActivity.this, "Salvo com Sucesso",
                    Toast.LENGTH_SHORT).show();
            limparCampos();
        } catch (Exception e) {
            System.err.println("<===========================================================>");
            e.printStackTrace();
            System.err.println("<===========================================================>");
            Toast.makeText(RegisterReceitaActivity.this, "Um Erro Ocorreu",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void updateValor() {
        String auxValor = tilValor.getEditText().getText().toString();
        String auxDescon = tilDescon.getEditText().getText().toString();
        String tv;

        if (!auxValor.isEmpty()) {
            double valor = Double.parseDouble(auxValor);
            if (!auxDescon.isEmpty()) {
                double descon = Double.parseDouble(auxDescon);
                descon = (descon / 100) * valor;
                tv = "Valor Total: R$" + (valor - descon);

            } else {
                tv = "Valor Total: R$" + valor;
            }
        } else {
            tv = "Valor Total: R$ 0,00";
        }
        tvValorTotal.setText(tv);
    }

    private void salvar(Categoria categoria) {
        try {
            SugarContext.init(RegisterDespesaActivity.this);
            categoria.save();
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
