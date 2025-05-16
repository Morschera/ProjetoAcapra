package br.edu.unifebe.projeto20

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class RegistroActivity : AppCompatActivity() {
    private lateinit var btnRegistro: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro_view)

        btnRegistro = findViewById(R.id.btnRegistra)
        btnRegistro.setOnClickListener {
            Toast.makeText(this, "Administrador cadastrado", Toast.LENGTH_SHORT).show()
    }
    }
}