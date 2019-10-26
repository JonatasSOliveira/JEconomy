package com.example.jeconomy.activitys;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeconomy.R;
import com.example.jeconomy.dialogs.DatePickerFragment;
import com.example.jeconomy.dialogs.RegisterCategoriaDialog;
import com.example.jeconomy.models.Categoria;
import com.example.jeconomy.models.FormaPagamento;
import com.example.jeconomy.models.Parcela;
import com.example.jeconomy.models.Receita;
import com.example.jeconomy.models.Usuario;
import com.google.android.material.textfield.TextInputLayout;
import com.orm.SugarContext;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RegisterReceitaActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, RegisterCategoriaDialog.OnInputSelected {

    private Spinner spCategoria, spTipo, spFormaPag;
    private List<Categoria> listCategoria;
    private TextInputLayout tilDataPv, tilDataSv, tilValor, tilDescon, tilQtdeParcela;
    private TextView tvFormaPag, tvValorTotal;
    private Button btnDataPv, btnDataSv, btnCadastrar;
    private Switch swPaga;
    private EditText etObs;
    private CheckBox cbObs;
    private Usuario user;
    private Date dataPv, dataSv;
    private byte dateOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_receita);

        setAttributes();

        Bundle bundle = getIntent().getBundleExtra("home");
        user = (Usuario) bundle.getSerializable("user");
        long userId = bundle.getLong("user_id");
        user.setId(userId);

        String[] arrTipo = {"VENDA", "SERVIÇO"};
        String[] arrFormaPag = {"ESCOLHA", "DINHEIRO", "CARTÃO"};

        tilDataPv.setHint("DATA DE PAGAMENTO");
        tilDataPv.setEnabled(false);
        tilDataSv.setHint("DATA DA VENDA");
        tilDataSv.setEnabled(false);
        tilDescon.getEditText().setText("0");

        try {
            updateCategoria();

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(RegisterReceitaActivity.this,
                    android.R.layout.simple_spinner_item, arrTipo);
            spTipo.setAdapter(arrayAdapter);

            arrayAdapter = new ArrayAdapter<>(RegisterReceitaActivity.this,
                    android.R.layout.simple_spinner_item, arrFormaPag);
            spFormaPag.setAdapter(arrayAdapter);

            spTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == 0) {
                        tilDataSv.setHint("DATA DA VENDA");
                    } else {
                        tilDataSv.setHint("DATA DO SERVIÇO");
                    }
                    tilDataSv.getEditText().setText("");
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            swPaga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    tvFormaPag.setEnabled(b);
                    spFormaPag.setEnabled(b);
                    spFormaPag.setSelection(0);
                    if (b) {
                        tvFormaPag.setVisibility(View.VISIBLE);
                        spFormaPag.setVisibility(View.VISIBLE);
                    } else {
                        tvFormaPag.setVisibility(View.INVISIBLE);
                        spFormaPag.setVisibility(View.INVISIBLE);
                    }

                    payChange();
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

            tilQtdeParcela.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String text = charSequence.toString();
                    if (text.isEmpty()) {
                        tilQtdeParcela.getEditText().setText("0");
                    }
                    payChange();
                }

                @Override
                public void afterTextChanged(Editable editable) {

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

            cbObs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    etObs.setEnabled(b);
                    etObs.setText("");
                }
            });

            btnCadastrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String etDataPv = tilDataPv.getEditText().getText().toString();
                    String etDataSv = tilDataSv.getEditText().getText().toString();
                    String auxValor = tilValor.getEditText().getText().toString();
                    String auxDescon = tilDescon.getEditText().getText().toString();
                    String obs = etObs.getText().toString().trim();
                    int auxCategoria = spCategoria.getSelectedItemPosition();
                    int formaPagItem = spFormaPag.getSelectedItemPosition();
                    int qtdeParcelas = Integer.parseInt(tilQtdeParcela.getEditText().getText().toString());

                    if (auxCategoria == 0 || etDataPv.isEmpty() || etDataSv.isEmpty() || auxValor.isEmpty()
                            || auxDescon.isEmpty() || (swPaga.isChecked() && formaPagItem == 0) || qtdeParcelas < 1
                            || (cbObs.isChecked() && obs.isEmpty())) {
                        Toast.makeText(RegisterReceitaActivity.this, "Preencha Todos os Campos",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Categoria categoria = listCategoria.get(auxCategoria - 2);

                        if (!categoria.isUsed()) {
                            categoria.setUsed(true);
                            save(categoria);
                        }

                        double valor = Double.parseDouble(auxValor);
                        double descon = Double.parseDouble(auxDescon);
                        double valorTotal = valor - (valor * descon / 100);

                        Receita receita = new Receita(valor, descon, valorTotal, qtdeParcelas, dataSv, categoria, user);

                        int tipo = spTipo.getSelectedItemPosition();

                        if (tipo == 0) {
                            receita.setTipoReceita("V");
                        } else {
                            receita.setTipoReceita("S");
                        }

                        if (swPaga.isChecked()) {
                            if (qtdeParcelas == 1) {
                                receita.setPago(true);
                            } else {
                                receita.setPago(false);
                            }

                            Parcela parcela = new Parcela(dataPv, null, 1, save(receita));
                            FormaPagamento formaPag = new FormaPagamento(valor, save(parcela));
                            if (formaPagItem == 1) {
                                formaPag.setTipo("D");
                            } else {
                                formaPag.setTipo("C");
                            }
                            save(formaPag);
                        } else {
                            receita.setPago(false);
                            Parcela parcela = new Parcela(null, dataPv, 1, save(receita));
                            save(parcela);
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
        Calendar dateActual = Calendar.getInstance();
        Calendar dateSelected = Calendar.getInstance();
        dateSelected.set(Calendar.YEAR, year);
        dateSelected.set(Calendar.MONTH, month);
        dateSelected.set(Calendar.DAY_OF_MONTH, day);

        if (((swPaga.isChecked()
                && dateOption == 0)
                || (spTipo.getSelectedItemPosition() == 0
                && dateOption == 1))
                && isDateLater(dateSelected, dateActual)) {
            String aviso;
            if (dateOption == 1) {
                aviso = "Selecione uma data de venda atual ou anterior";
            } else {
                aviso = "Selecione uma data de pagamento atual ou anterior";
            }
            Toast.makeText(this, aviso, Toast.LENGTH_SHORT).show();
        } else if ((!swPaga.isChecked()) && isDatePrevious(dateSelected, dateActual)) {
            Toast.makeText(this, "Selecione uma data de vencimento posterior ao dia atual",
                    Toast.LENGTH_SHORT).show();
        } else {
            setDate(dateSelected, dateOption);
        }
    }

    private void setDate(Calendar dateSelected, int dateOption) {
        String data = DateFormat.getDateInstance(DateFormat.SHORT).format(dateSelected.getTime());

        if (dateOption == 0) {
            tilDataPv.getEditText().setText(data);
            dataPv = dateSelected.getTime();
        } else {
            tilDataSv.getEditText().setText(data);
            dataSv = dateSelected.getTime();
        }
    }

    private void clearInputs() {
        String tv = "Valor Total: R$ 0,00";
        spCategoria.setSelection(0);
        spTipo.setSelection(0);
        spFormaPag.setSelection(0);
        tilDataPv.getEditText().setText("");
        tilDataSv.getEditText().setText("");
        tilValor.getEditText().setText("");
        tilDescon.getEditText().setText("");
        tvValorTotal.setText(tv);
        etObs.setText("");
        cbObs.setChecked(false);
        tilQtdeParcela.getEditText().setText("1");
        swPaga.setChecked(false);
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

    private Receita save(Receita receita) {
        SugarContext.init(RegisterReceitaActivity.this);
        receita.save();
        receita = (Select.from(Receita.class).list()).get((int) Select.from(Receita.class).count() - 1);
        SugarContext.terminate();
        Toast.makeText(RegisterReceitaActivity.this, "SALVO COM SUCESSO", Toast.LENGTH_SHORT).show();
        return receita;
    }

    private void save(Categoria categoria) {
        try {
            SugarContext.init(RegisterReceitaActivity.this);
            categoria.save();
            SugarContext.terminate();
        } catch (Exception e) {
            System.err.println("<===========================================================>");
            e.printStackTrace();
            System.err.println("<===========================================================>");
        }
    }

    private void save(FormaPagamento formaPagamento){
        try {
            SugarContext.init(RegisterReceitaActivity.this);
            formaPagamento.save();
            SugarContext.terminate();
        } catch (Exception e) {
            System.err.println("<===========================================================>");
            e.printStackTrace();
            System.err.println("<===========================================================>");
        }
    }

    private Parcela save(Parcela parcela) {
        SugarContext.init(RegisterReceitaActivity.this);
        parcela.save();
        parcela = (Select.from(Parcela.class).where(Condition.prop("RECEITA").eq(parcela.getReceita().getId()))
                .and(Condition.prop("N_PARCELA").eq(parcela.getnParcela())).list()).get(0);
        SugarContext.terminate();
        clearInputs();
        return parcela;
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

    private void setAttributes() {
        spCategoria = findViewById(R.id.sp_categoria_registerreceita);
        spTipo = findViewById(R.id.sp_tipo_registerreceita);
        spFormaPag = findViewById(R.id.sp_formapag_registerreceita);
        tilDataPv = findViewById(R.id.til_datapv_registerreceita);
        tilDataSv = findViewById(R.id.til_datasv_registerreceita);
        tilValor = findViewById(R.id.til_valor_registerreceita);
        tilDescon = findViewById(R.id.til_desconto_registerreceita);
        tilQtdeParcela = findViewById(R.id.til_qtdeparcelas_resgisterreceita);
        tvFormaPag = findViewById(R.id.tv_formapag_registerreceita);
        tvValorTotal = findViewById(R.id.tv_valortotal_registerreceita);
        btnDataPv = findViewById(R.id.btn_datapv_registerreceita);
        btnDataSv = findViewById(R.id.btn_datasv_registerreceita);
        btnCadastrar = findViewById(R.id.btn_cadastrar_registerreceita);
        swPaga = findViewById(R.id.sw_parcelapaga_registerreceita);
        etObs = findViewById(R.id.mt_obs_registerreceita);
        cbObs = findViewById(R.id.cb_obs_registerreceita);


        tilDataPv.getEditText().setEnabled(false);
        tilDataPv.setHint("DATA DE PAGAMENTO");
        etObs.setEnabled(false);
        tilQtdeParcela.getEditText().setText("1");
        tvFormaPag.setEnabled(false);
        spFormaPag.setEnabled(false);
        tvFormaPag.setVisibility(View.INVISIBLE);
        spFormaPag.setVisibility(View.INVISIBLE);
    }

    private void payChange() {
        int qtdeParcela = Integer.parseInt(tilQtdeParcela.getEditText().getText().toString());
        String text;
        if (swPaga.isChecked()) {
            text = "PAGAMENTO";

        } else {
            text = "VENCIMENTO";
        }
        if (qtdeParcela > 1) {
            text = text + " - 1º PARCELA";
        }
        tilDataPv.setHint(text);
    }

    private boolean isDateLater(Calendar dateSelected, Calendar dateActual) {
        int yearSelected = dateSelected.get(Calendar.YEAR);
        int monthSelected = dateSelected.get(Calendar.MONTH);
        int daySelected = dateSelected.get(Calendar.DAY_OF_MONTH);
        int yearActual = dateActual.get(Calendar.YEAR);
        int monthActual = dateActual.get(Calendar.MONTH);
        int today = dateActual.get(Calendar.DAY_OF_MONTH);

        return yearSelected > yearActual
                || yearSelected == yearActual
                && (monthSelected > monthActual
                || (monthSelected == monthActual
                && daySelected > today));
    }

    private boolean isDatePrevious(Calendar dateSelected, Calendar dateActual) {
        int yearSelected = dateSelected.get(Calendar.YEAR);
        int monthSelected = dateSelected.get(Calendar.MONTH);
        int daySelected = dateSelected.get(Calendar.DAY_OF_MONTH);
        int yearActual = dateActual.get(Calendar.YEAR);
        int monthActual = dateActual.get(Calendar.MONTH);
        int today = dateActual.get(Calendar.DAY_OF_MONTH);

        return yearSelected < yearActual
                || yearSelected == yearActual
                && (monthSelected < monthActual
                || (monthSelected == monthActual
                && daySelected <= today));
    }
}
