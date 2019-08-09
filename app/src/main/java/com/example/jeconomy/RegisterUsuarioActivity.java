package com.example.jeconomy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jeconomy.models.Usuario;
import com.google.android.material.textfield.TextInputLayout;
import com.orm.SugarContext;

public class RegisterUsuarioActivity extends AppCompatActivity {

    private TextInputLayout tilNome, tilContato, tilLogin, tilSenha, tilSenhaConfirm;
    private Button btnCadastrar;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_usuario);
        SugarContext.init(RegisterUsuarioActivity.this);

        tilNome = findViewById(R.id.til_nome_cadastrousuario);
        tilContato = findViewById(R.id.til_contato_cadastrousuario);
        tilLogin = findViewById(R.id.til_login_cadastrousuario);
        tilSenha = findViewById(R.id.til_senha_cadastrousuario);
        tilSenhaConfirm = findViewById(R.id.til_senhaconfirm_cadastrousuario);
        btnCadastrar = findViewById(R.id.btn_cadastrar_cadastrousuario);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = tilNome.getEditText().getText().toString().trim().toUpperCase();
                String login = tilLogin.getEditText().getText().toString().trim();
                String contato = tilContato.getEditText().getText().toString().trim();
                String senha = tilSenha.getEditText().getText().toString().trim();
                String senhaConfirm = tilSenhaConfirm.getEditText().getText().toString().trim();

                if (nome.isEmpty() || login.isEmpty() || contato.isEmpty() || senha.isEmpty() || senhaConfirm.isEmpty()) {
                    Toast.makeText(RegisterUsuarioActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                    clearText();
                } else if (contato.length() != 11) {
                    Toast.makeText(RegisterUsuarioActivity.this, "Número de Telefone Inválido", Toast.LENGTH_SHORT).show();
                    clearText();
                } else if (!senha.equals(senhaConfirm)) {
                    Toast.makeText(RegisterUsuarioActivity.this, "Senhas não correspondentes", Toast.LENGTH_SHORT).show();
                    clearText();
                } else {
                    usuario = new Usuario(nome, contato, login, senha);
                    try {
                        usuario.save();
                        Toast.makeText(RegisterUsuarioActivity.this, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                        clearText();
                    } catch (Exception e) {
                        System.err.println("<===========================================================>");
                        e.printStackTrace();
                        System.err.println("<===========================================================>");
                        Toast.makeText(RegisterUsuarioActivity.this, "Um erro ocorreu durante o " +
                                "processo de salvamento\nPor favor, tente novamente", Toast.LENGTH_SHORT).show();
                        clearText();
                    }

                }
            }
        });
    }

    private void clearText() {
        tilNome.getEditText().setText("");
        tilContato.getEditText().setText("");
        tilLogin.getEditText().setText("");
        tilSenha.getEditText().setText("");
        tilSenhaConfirm.getEditText().setText("");
    }

}
