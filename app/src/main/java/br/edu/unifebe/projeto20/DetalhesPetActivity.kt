package br.edu.unifebe.projeto20

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import br.edu.unifebe.projeto20.Model.Pet
import android.widget.TextView
import android.widget.ImageView
import br.edu.unifebe.projeto20.view.FormularioAdocaoActivity
import com.bumptech.glide.Glide

class DetalhesPetActivity : AppCompatActivity() {

    private lateinit var btnVoltar: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_pet)
        btnVoltar = findViewById(R.id.btnVoltar)

        val pet = intent.getParcelableExtra<Pet>("pet")

        pet?.let {
            findViewById<TextView>(R.id.txtNome).text = it.nome
            findViewById<TextView>(R.id.txtDescricao).text = it.descricao
            findViewById<TextView>(R.id.txtIdade).text = "Idade: ${it.idade}"
            findViewById<TextView>(R.id.txtSexo).text = "Sexo: ${it.sexo}"
            findViewById<TextView>(R.id.txtTipo).text = "Tipo: ${it.tipo}"
            findViewById<TextView>(R.id.txtPorte).text = "Porte: ${it.porte}"
            findViewById<TextView>(R.id.txtVacinado).text = "Vacinado: ${if (it.vacinado) "Sim" else "Não"}"
            findViewById<TextView>(R.id.txtCastrado).text = "Castrado: ${if (it.castrado) "Sim" else "Não"}"

            val imageView = findViewById<ImageView>(R.id.imgPet)
            Glide.with(this).load(it.imagemUrl).into(imageView)
        }

        val logo = findViewById<ImageView>(R.id.Logo)

        logo.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val btnAdotar = findViewById<Button>(R.id.btnAdotar)

        btnAdotar.setOnClickListener {
            pet?.let {
                val intent = Intent(this, FormularioAdocaoActivity::class.java)
                intent.putExtra("animal_interesse", it.nome)
                startActivity(intent)
            }
        }

        btnVoltar.setOnClickListener {
            finish()
        }
    }
}
