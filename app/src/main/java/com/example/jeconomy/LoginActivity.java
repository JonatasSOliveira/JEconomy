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

public class LoginActivity extends AppCompatActivity {

    private TextView tvCadastro;
    private TextInputLayout tilLogin, tilSenha;
    private Button btnAcessar;
    private Usuario usuario;
    private List<Usuario> listUsuario;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvCadastro = findViewById(R.id.tv_cadastrousuario_login);
        tilLogin = findViewById(R.id.til_login_login);
        tilSenha = findViewById(R.id.til_senha_senha);
        btnAcessar = findViewById(R.id.btn_entrar_login);

        tvCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterUsuarioActivity.class));
            }
        });

        btnAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = tilLogin.getEditText().getText().toString().trim();
                String senha = tilSenha.getEditText().getText().toString().trim();
                if (login.isEmpty() || senha.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Preencha Todos os Campos", Toast.LENGTH_SHORT).show();
                    clearInputs();
                } else {
                    try {
                        SugarContext.init(LoginActivity.this);
                        usuario = (Usuario.find(Usuario.class, "login = ?", new String[]{login})).get(0);
                        SugarContext.terminate();
                        if (!usuario.getSenha().equals(senha)) {
                            Toast.makeText(LoginActivity.this, "Senha Incorreta", Toast.LENGTH_SHORT).show();
                            clearInputs();
                        } else {
                            Intent intent = new Intent (LoginActivity.this, HomeActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("user", usuario);
                            intent.putExtra("tela_login", bundle);
                            startActivity(intent);
                            clearInputs();
                        }
                    } catch (Exception e) {
                        System.err.println("<===========================================================>");
                        e.printStackTrace();
                        System.err.println("<===========================================================>");
                        Toast.makeText(LoginActivity.this, "Login Incorreto", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void clearInputs() {
        tilLogin.getEditText().setText("");
        tilSenha.getEditText().setText("");
    }
}
