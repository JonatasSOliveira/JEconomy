package com.example.jeconomy.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jeconomy.R;
import com.example.jeconomy.RegisterDespesaActivity;


public class HomeFragment extends Fragment {

    private Button btnDespesa;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btnDespesa = view.findViewById(R.id.btn_despesa_home);

        btnDespesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), RegisterDespesaActivity.class));
            }
        });

        return view;
    }

}
