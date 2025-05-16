package br.edu.unifebe.projeto20

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AdmFormularioActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.adm_formularios)

        val btnPet = findViewById<Button>(R.id.btnPet)
        val btnRegistrarAdmin = findViewById<Button>(R.id.btnRegistraAdm)
        val btnFormularios = findViewById<Button>(R.id.btnForms)

        val intentMain = Intent(this, MainActivity::class.java)

        btnPet.setOnClickListener {
            startActivity(intentMain)
        }

        btnRegistrarAdmin.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        btnFormularios.setOnClickListener {
            Toast.makeText(this, "Você já está nesta tela.", Toast.LENGTH_SHORT).show()
        }
    }
}

