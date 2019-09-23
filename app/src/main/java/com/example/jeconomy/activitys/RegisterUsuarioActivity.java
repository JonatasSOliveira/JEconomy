package com.example.jeconomy.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jeconomy.R;
import com.example.jeconomy.models.Usuario;
import com.google.android.material.textfield.TextInputLayout;
import com.orm.SugarContext;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class RegisterUsuarioActivity extends AppCompatActivity {

    private TextInputLayout tilNome, tilLogin, tilSenha, tilSenhaConfirm;
    private Button btnCadastrar;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_usuario);
        SugarContext.init(RegisterUsuarioActivity.this);

        tilNome = findViewById(R.id.til_nome_cadastrousuario);
        tilLogin = findViewById(R.id.til_login_cadastrousuario);
        tilSenha = findViewById(R.id.til_senha_cadastrousuario);
        tilSenhaConfirm = findViewById(R.id.til_senhaconfirm_cadastrousuario);
        btnCadastrar = findViewById(R.id.btn_cadastrar_cadastrousuario);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = tilNome.getEditText().getText().toString().trim().toUpperCase();
                String login = tilLogin.getEditText().getText().toString().trim();
                String senha = tilSenha.getEditText().getText().toString().trim();
                String senhaConfirm = tilSenhaConfirm.getEditText().getText().toString().trim();

                if (nome.isEmpty() || login.isEmpty() || senha.isEmpty() || senhaConfirm.isEmpty()) {
                    Toast.makeText(RegisterUsuarioActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                    clearText();
                } else {
                    try {
                        SugarContext.init(RegisterUsuarioActivity.this);
                        List<Usuario> listUsuario = Usuario.find(Usuario.class, "login = ?", login);
                        SugarContext.terminate();

                        if(listUsuario.get(0) != null){
                            Toast.makeText(RegisterUsuarioActivity.this, "Login já usado", Toast.LENGTH_SHORT).show();
                        }
                        clearText();
                    } catch (Exception e) {
                        if (!senha.equals(senhaConfirm)) {
                            Toast.makeText(RegisterUsuarioActivity.this, "Senhas não correspondentes", Toast.LENGTH_SHORT).show();
                            clearText();
                        } else {
                            try {
                                usuario = new Usuario(nome, login, senha);
                            } catch (NoSuchAlgorithmException e1) {
                                e1.printStackTrace();
                            } catch (UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                            }
                            try {
                                SugarContext.init(RegisterUsuarioActivity.this);
                                usuario.save();
                                SugarContext.terminate();
                                Toast.makeText(RegisterUsuarioActivity.this, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                                clearText();
                                finish();
                            } catch (Exception err) {
                                System.err.println("<===========================================================>");
                                err.printStackTrace();
                                System.err.println("<===========================================================>");
                                Toast.makeText(RegisterUsuarioActivity.this, "Um erro ocorreu durante o " +
                                        "processo de salvamento\nPor favor, tente novamente", Toast.LENGTH_SHORT).show();
                                clearText();
                            }

                        }
                    }

                }

            }
        });
    }

    private void clearText() {
        tilNome.getEditText().setText("");
        tilLogin.getEditText().setText("");
        tilSenha.getEditText().setText("");
        tilSenhaConfirm.getEditText().setText("");
    }

}
