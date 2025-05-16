package br.edu.unifebe.projeto20

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

    class LoginActivity : AppCompatActivity() {

        private lateinit var edtUsuario: EditText
        private lateinit var edtSenha: EditText
        private lateinit var btnLogin: Button

        private val USUARIO_CORRETO = "admin"
        private val SENHA_CORRETA = "1234"

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.login_view)

            edtUsuario = findViewById(R.id.txtEmail)
            edtSenha = findViewById(R.id.txtSenha)
            btnLogin = findViewById(R.id.btnLogar)

            btnLogin.setOnClickListener {
                val usuario = edtUsuario.text.toString()
                val senha = edtSenha.text.toString()

                if (usuario == USUARIO_CORRETO && senha == SENHA_CORRETA) {
                    Toast.makeText(this, "Acesso liberado!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, AdmFormularioActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Usuário ou senha inválidos!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }