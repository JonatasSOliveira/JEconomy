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
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeconomy.R;
import com.example.jeconomy.dialogs.DatePickerFragment;
import com.example.jeconomy.dialogs.RegisterCategoriaDialog;
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
    private Usuario user;
    private Date dataPv, dataSv;
    private byte dateOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_receita);

        /*setAttributes();

        Bundle bundle = getIntent().getBundleExtra("home");
        user = (Usuario) bundle.getSerializable("user");
        long userId = bundle.getLong("user_id");
        user.setId(userId);

        String[] arrTipoPag = {"PAGA", "A PAGAR"}, arrTipo = {"VENDA", "SERVIÇO"};
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
                        payTypeChange(true);
                    } else {
                        payTypeChange(false);
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
                    tilDataSv.getEditText().setText("");
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

                        if (!categoria.isUsed()) {
                            categoria.setUsed(true);
                            save(categoria);
                        }

                        double valor = Double.parseDouble(auxValor);
                        double descon = Double.parseDouble(auxDescon);
                        double valorTotal = valor - (valor * descon / 100);

                        Receita receita = new Receita(valor, descon, valorTotal, 0, categoria, user);

                        int tipo = spTipo.getSelectedItemPosition();

                        if (tipo == 0) {
                            receita.setVendServ("V", dataSv);
                        } else {
                            receita.setVendServ("S", dataSv);
                        }
                        if (tipoPag == 0) {
                            receita.setPago(true);
                            receita.setDataPag(dataPv);
                            if (formaPag == 1) {
                                receita.setFormaPagamento("D");
                            } else {
                                receita.setFormaPagamento("C");
                            }
                            receita.setPagVenc(true, dataPv);
                        } else {
                            receita.setPagVenc(false, dataPv);
                        }

                        save(receita);

                    }
                }
            });


        } catch (Exception e) {
            System.err.println("<===========================================================>");
            e.printStackTrace();
            System.err.println("<===========================================================>");
        }*/


    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar dateActual = Calendar.getInstance();
        Calendar dateSelected = Calendar.getInstance();
        dateSelected.set(Calendar.YEAR, year);
        dateSelected.set(Calendar.MONTH, month);
        dateSelected.set(Calendar.DAY_OF_MONTH, day);

        if (((spTipoPag.getSelectedItemPosition() == 0
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
        } else if (spTipoPag.getSelectedItemPosition() == 1 && isDatePrevious(dateSelected, dateActual)) {
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
            clearInputs();
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

    private void save(Categoria categoria) {
        try {
            SugarContext.init(RegisterReceitaActivity.this);
            categoria.save();
            SugarContext.terminate();
            clearInputs();
            Toast.makeText(RegisterReceitaActivity.this, "SALVO COM SUCESSO", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            System.err.println("<===========================================================>");
            e.printStackTrace();
            System.err.println("<===========================================================>");
            Toast.makeText(RegisterReceitaActivity.this, "UM ERRO OCORREU", Toast.LENGTH_SHORT).show();
        }
    }

    private void setAttributes() {
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
    }

    private void payTypeChange(boolean isPago) {
        if (isPago) {
            tilDataPv.setHint("DATA DE PAGAMENTO");
            spFormaPag.setVisibility(View.VISIBLE);
            tvFormaPag.setVisibility(View.VISIBLE);
        } else {
            tilDataPv.setHint("DATA DE VENCIMENTO");
            spFormaPag.setVisibility(View.INVISIBLE);
            tvFormaPag.setVisibility(View.INVISIBLE);
        }
        tilDataPv.getEditText().setText("");
        spFormaPag.setEnabled(isPago);
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
