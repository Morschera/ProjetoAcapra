package br.edu.unifebe.projeto20

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.unifebe.projeto20.databinding.RegistroViewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistroActivity : AppCompatActivity() {

    private lateinit var binding: RegistroViewBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegistroViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.btnRegistra.setOnClickListener {
            val nome = binding.txtNomeReg.text.toString().trim()
            val email = binding.txtEmailReg.text.toString().trim()
            val telefone = binding.txtTelefoneReg.text.toString().trim()
            val senha = binding.txtSenhaReg.text.toString().trim()

            if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid ?: ""

                        val usuario = hashMapOf(
                            "nome" to nome,
                            "email" to email,
                            "telefone" to telefone
                        )

                        db.collection("usuarios").document(userId).set(usuario)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Usuário registrado com sucesso!", Toast.LENGTH_SHORT).show()
                                finish() // volta para tela anterior
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Erro ao salvar dados: ${it.message}", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(this, "Erro: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        binding.btnVoltar.setOnClickListener {
            finish()
        }
    }
}
