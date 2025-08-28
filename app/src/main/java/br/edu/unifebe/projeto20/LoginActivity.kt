package br.edu.unifebe.projeto20

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {

        private lateinit var edtUsuario: EditText
        private lateinit var edtSenha: EditText
        private lateinit var btnLogin: Button
        private lateinit var auth: FirebaseAuth

        @SuppressLint("MissingInflatedId")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.login_view)
            auth = Firebase.auth

            edtUsuario = findViewById(R.id.txtEmail)
            edtSenha = findViewById(R.id.txtSenha)
            btnLogin = findViewById(R.id.btnLogin)

            btnLogin.setOnClickListener {
                val usuario = edtUsuario.text.toString().trim()
                val senha = edtSenha.text.toString().trim()

                if (usuario.isEmpty() || senha.isEmpty()) {
                    Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                auth.signInWithEmailAndPassword(usuario, senha)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            val intent = Intent(this, ListaFormulariosActivity::class.java)
                            intent.putExtra("USER_EMAIL", user?.email)
                            Toast.makeText(this, "Bem-vindo, ${user?.email}", Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                            finish()
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(this, "Usuário ou senha inválidos!", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
}