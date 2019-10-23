package com.example.jeconomy.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.jeconomy.R;
import com.example.jeconomy.dialogs.DatePickerFragment;
import com.example.jeconomy.models.Despesa;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class PagamentoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private TextView tvCategoria, tvDataVenc, tvValor;
    private TextInputLayout tilDataPag;
    private Button btnDataPag;
    private Despesa despesa;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);

        Bundle bundle = getIntent().getBundleExtra("tela_pagamento");
        despesa = (Despesa) bundle.getSerializable("despesa");

        tvCategoria = findViewById(R.id.tv_infocategoria_pagamento);
        tvDataVenc = findViewById(R.id.tv_infodatavenc_pagamento);
        tvValor = findViewById(R.id.tv_infovalor_pagamento);
        tilDataPag = findViewById(R.id.til_data_pagamento);
        btnDataPag = findViewById(R.id.btn_data_pagamento);
        tilDataPag = findViewById(R.id.til_data_pagamento);

        tilDataPag.setEnabled(false);

        String data, valor;

        //data = DateFormat.getDateInstance(DateFormat.SHORT).format(despesa.getDataVenc());
        //tvDataVenc.setText(data);
        tvCategoria.setText(despesa.getCategoria().getNome());
        btnDataPag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });


        valor = "R$ " + despesa.getValor();
        tvValor.setText(valor);
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        String dateSelected = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
        tilDataPag.getEditText().setText(dateSelected);
        date = calendar.getTime();
    }
}
