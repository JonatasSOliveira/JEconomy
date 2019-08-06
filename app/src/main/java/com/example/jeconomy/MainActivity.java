package com.example.jeconomy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeconomy.models.Usuario;
import com.google.android.material.textfield.TextInputLayout;
import com.orm.SugarContext;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvCadastro;
    private TextInputLayout tilLogin, tilSenha;
    private Button btnAcessar;
    private Usuario usuario;
    private List<Usuario> listUsuario;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCadastro = findViewById(R.id.tv_cadastrousuario_login);
        tilLogin = findViewById(R.id.til_login_login);
        tilSenha = findViewById(R.id.til_senha_senha);
        btnAcessar = findViewById(R.id.btn_entrar_login);

        tvCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterUsuarioActivity.class));
            }
        });

        btnAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = tilLogin.getEditText().getText().toString();
                String senha = tilSenha.getEditText().getText().toString();
                if(login.isEmpty() || senha.isEmpty()){
                    Toast.makeText(MainActivity.this, "Preencha Todos os Campos", Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        SugarContext.init(MainActivity.this);
                        usuario =  (Usuario.find(Usuario.class, "login = ?", new String[]{login})).get(0);
                        SugarContext.terminate();
                        if(!usuario.getSenha().equals(senha)){
                            Toast.makeText(MainActivity.this, "Senha Incorreta", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        }
                    }
                    catch (Exception e){
                        System.err.println("<===========================================================>");
                        e.printStackTrace();
                        System.err.println("<===========================================================>");
                        Toast.makeText(MainActivity.this, "Login Incorreto", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
