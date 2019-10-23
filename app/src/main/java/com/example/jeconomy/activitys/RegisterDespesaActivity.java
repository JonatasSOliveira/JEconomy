package com.example.jeconomy.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeconomy.R;
import com.example.jeconomy.dialogs.DatePickerFragment;
import com.example.jeconomy.dialogs.RegisterCategoriaDialog;
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

    private Spinner spCategoria, spFormaPag, spTipoPag;
    private List<Categoria> listCategoria;
    private Button btnData, btnCadastrar;
    private TextInputLayout tilData, tilValor, tilQtdeParcela;
    private Date date;
    private TextView tvFormaPag;
    private Usuario user;
    private CheckBox cbObs;
    private EditText etObs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_despesa);

        Bundle bundle = getIntent().getBundleExtra("home");
        user = (Usuario) bundle.getSerializable("user");
        long userId = bundle.getLong("user_id");
        user.setId(userId);

        setAtributes();

        String[] listFormaPag = {"ESCOLHA", "DINHEIRO", "CARTÃO"}, listTipo = {"PAGA", "PARCELADA"};

        try {
            updateCategoria();

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(RegisterDespesaActivity.this,
                    android.R.layout.simple_spinner_item, listFormaPag);
            spFormaPag.setAdapter(arrayAdapter);

            arrayAdapter = new ArrayAdapter<>(RegisterDespesaActivity.this,
                    android.R.layout.simple_spinner_item, listTipo);
            spTipoPag.setAdapter(arrayAdapter);

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
                    int tipoDespesa = spTipoPag.getSelectedItemPosition();
                    String auxValor = tilValor.getEditText().getText().toString();
                    String data = tilData.getEditText().getText().toString();
                    String obs = etObs.getText().toString().trim();

                    if (categoriaItem == 0 || auxValor.isEmpty() || data.isEmpty() ||
                            (formaPagItem == 0 && tipoDespesa == 0) || (cbObs.isChecked() && obs.equals(""))) {
                        Toast.makeText(RegisterDespesaActivity.this, "Preencha todos os Campos",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        double valor = Double.parseDouble(auxValor);
                        Categoria categoria = listCategoria.get(categoriaItem - 2);

                        if (!categoria.isUsed()) {
                            categoria.setUsed(true);
                            save(categoria);
                        }

                        Despesa despesa = new Despesa(valor, obs, 0, categoria, user);

                        if (spTipoPag.getSelectedItemPosition() == 0) {
                            if (formaPagItem == 1) {
                                //despesa.setFormaPag("D");
                            } else {
                                //despesa.setFormaPag("C");
                            }
                            //despesa.setPagVenc(true, date);

                        } else {
                          //despesa.setPagVenc(false, date);
                        }
                        save(despesa);
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

        if (spTipoPag.getSelectedItemPosition() == 0
                && isDateLater(dateSelected, dateActual)) {
            Toast.makeText(this, "Selecione uma data de pagamento atual ou anterior", Toast.LENGTH_SHORT).show();

        } else if (spTipoPag.getSelectedItemPosition() == 1 && isDatePrevious(dateSelected, dateActual)) {
            Toast.makeText(this, "Selecione uma data de vencimento posterior ao dia atual",
                    Toast.LENGTH_SHORT).show();
        } else {
            setDate(dateSelected);
        }
    }

    private void setDate(Calendar dateSelected) {
        String date = DateFormat.getDateInstance(DateFormat.SHORT).format(dateSelected.getTime());
        tilData.getEditText().setText(date);
        this.date = dateSelected.getTime();
    }

    private void clearInputs() {
        spCategoria.setSelection(0);
        spFormaPag.setSelection(0);
        spTipoPag.setSelection(0);
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

    private void save(Despesa despesa) {
        try {
            SugarContext.init(RegisterDespesaActivity.this);
            despesa.save();
            SugarContext.terminate();
            clearInputs();
            Toast.makeText(RegisterDespesaActivity.this, "SALVO COM SUCESSO", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            System.err.println("<===========================================================>");
            e.printStackTrace();
            System.err.println("<===========================================================>");
            Toast.makeText(RegisterDespesaActivity.this, "UM ERRO OCORREU", Toast.LENGTH_SHORT).show();
        }
    }

    private void save(Categoria categoria) {
        try {
            SugarContext.init(RegisterDespesaActivity.this);
            categoria.save();
            SugarContext.terminate();
            clearInputs();
            Toast.makeText(RegisterDespesaActivity.this, "SALVO COM SUCESSO", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            System.err.println("<===========================================================>");
            e.printStackTrace();
            System.err.println("<===========================================================>");
            Toast.makeText(RegisterDespesaActivity.this, "UM ERRO OCORREU", Toast.LENGTH_SHORT).show();
        }
    }

    private void payTypeChange(boolean isPago) {
        if (isPago) {
            tilData.setHint("DATA DE PAGAMENTO");
            spFormaPag.setVisibility(View.VISIBLE);
            tvFormaPag.setVisibility(View.VISIBLE);
        } else {
            tilData.setHint("DATA DE VENCIMENTO");
            spFormaPag.setVisibility(View.INVISIBLE);
            tvFormaPag.setVisibility(View.INVISIBLE);
        }
        tilData.getEditText().setText("");
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

    private void setAtributes() {
        spCategoria = findViewById(R.id.sp_categoria_registerdespesa);
        spFormaPag = findViewById(R.id.sp_formapag_registerdespesa);
        spTipoPag = findViewById(R.id.sp_tipo_registerdespesa);
        btnCadastrar = findViewById(R.id.btn_cadastrar_registerdespesa);
        btnData = findViewById(R.id.btn_data_registerdespesa);
        tilData = findViewById(R.id.til_data_registerdespesa);
        tilValor = findViewById(R.id.til_valor_registerdespesa);
        tvFormaPag = findViewById(R.id.tv_formapag_registerdespesa);
        cbObs = findViewById(R.id.cb_obs_registerdespesa);
        etObs = findViewById(R.id.mt_obs_registerdespesa);
        tilQtdeParcela = findViewById(R.id.til_qtdeparcelas_resgisterdespesa);

        tilData.getEditText().setEnabled(false);
        tilData.setHint("DATA DE PAGAMENTO");
        etObs.setEnabled(false);
        tilQtdeParcela.setEnabled(false);
        tilQtdeParcela.setVisibility(View.INVISIBLE);
    }

}
