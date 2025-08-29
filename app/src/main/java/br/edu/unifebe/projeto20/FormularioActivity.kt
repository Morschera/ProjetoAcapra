package br.edu.unifebe.projeto20

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FormularioActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.formulario_view)

        val btnPet = findViewById<Button>(R.id.btnPet)
        val btnRegistrarAdmin = findViewById<Button>(R.id.btnRegistraAdm)
        val btnFormularios = findViewById<Button>(R.id.btnForms)

        btnPet.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnRegistrarAdmin.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }

        btnFormularios.setOnClickListener {
            Toast.makeText(this, "Você já está nesta tela.", Toast.LENGTH_SHORT).show()
        }



        val btnVoltar = findViewById<Button>(R.id.btnVoltar)
        btnVoltar.setOnClickListener {
            finish()
        }

    }
}