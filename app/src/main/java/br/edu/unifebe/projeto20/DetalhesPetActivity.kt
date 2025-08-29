package br.edu.unifebe.projeto20

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.unifebe.projeto20.Model.Pet
import android.widget.TextView
import android.widget.ImageView
import com.bumptech.glide.Glide

class DetalhesPetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_pet)

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
    }
}
