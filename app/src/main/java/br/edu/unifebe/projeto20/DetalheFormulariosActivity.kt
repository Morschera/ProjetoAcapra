package br.edu.unifebe.projeto20

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore

class DetalheFormularioActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var btnVoltar: ImageButton
    private lateinit var containerImagens: LinearLayout

    companion object {
        const val EXTRA_FORM_ID = "formId"
        private const val TAG = "DetalheFormularioActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_formularios)

        val formId = intent.getStringExtra(EXTRA_FORM_ID)
        if (formId == null) {
            Toast.makeText(this, "Formulário não encontrado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        containerImagens = findViewById(R.id.containerImagens)

        findViewById<ImageView>(R.id.Logo).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnVoltar = findViewById(R.id.btnVoltar)
        btnVoltar.setOnClickListener { finish() }

        carregarFormulario(formId)
    }

    private fun carregarFormulario(formId: String) {
        db.collection("formulários").document(formId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {

                    findViewById<TextView>(R.id.tvNomeCompleto).text =
                        "Nome: ${document.getString("nomeCompleto")}"
                    findViewById<TextView>(R.id.tvTelefone).text =
                        "Telefone: ${document.getString("telefone")}"
                    findViewById<TextView>(R.id.tvAnimalInteresse).text =
                        "Animal de Interesse: ${document.getString("animalInteresse")}"
                    findViewById<TextView>(R.id.tvOutrosAnimais).text =
                        "Outros Animais: ${document.getString("outrosAnimais")}"
                    findViewById<TextView>(R.id.tvCondicoes).text =
                        "Condições: ${document.getString("condicoesFisicasFinanceiras")}"
                    findViewById<TextView>(R.id.tvMoradia).text =
                        "Moradia: ${document.getString("moradia")}"
                    findViewById<TextView>(R.id.tvCidadeUf).text =
                        "Cidade/UF: ${document.getString("cidade")}, ${document.getString("uf")}"
                    findViewById<TextView>(R.id.tvBairro).text =
                        "Bairro: ${document.getString("bairro")}"
                    findViewById<TextView>(R.id.tvEndereco).text =
                        "Endereço: ${document.getString("ruaNumero")}, CEP: ${document.getString("cep")}"

                    val idade = document.getLong("idade") ?: 0
                    findViewById<TextView>(R.id.tvIdade).text = "Idade: $idade"

                    val renda = document.getLong("rendaMensal") ?: 0
                    findViewById<TextView>(R.id.tvRenda).text = "Renda: $renda"

                    val localSeguro = document.getBoolean("localSeguro") ?: false
                    findViewById<TextView>(R.id.tvLocalSeguro).text =
                        "Local Seguro: ${if (localSeguro) "Sim" else "Não"}"

                    val castradosVacinados = document.getBoolean("castradosVacinados") ?: false
                    findViewById<TextView>(R.id.tvCastradosVacinados).text =
                        "Castrados/Vacinados: ${if (castradosVacinados) "Sim" else "Não"}"

                    val concordaVacinacao = document.getBoolean("concordaVacinacao") ?: false
                    findViewById<TextView>(R.id.tvVacinacao).text =
                        "Concorda Vacinação: ${if (concordaVacinacao) "Sim" else "Não"}"

                    val concordaResidencia = document.getBoolean("concordaResidencia") ?: false
                    findViewById<TextView>(R.id.tvConcordaResidencia).text =
                        "Concorda Residência: ${if (concordaResidencia) "Sim" else "Não"}"

                    val taxaDoacao = document.getBoolean("taxaDoacao") ?: false
                    findViewById<TextView>(R.id.tvTaxaDoacao).text =
                        "Aceita Taxa Doação: ${if (taxaDoacao) "Sim" else "Não"}"

                    val imagens = document.get("imagens") as? List<String> ?: emptyList()
                    containerImagens.removeAllViews()

                    for (url in imagens) {
                        val imageView = ImageView(this).apply {
                            layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                400
                            ).apply {
                                setMargins(0, 16, 0, 16)
                            }
                            scaleType = ImageView.ScaleType.CENTER_CROP
                            adjustViewBounds = true
                        }
                        Glide.with(this).load(url).into(imageView)
                        containerImagens.addView(imageView)
                    }

                } else {
                    Toast.makeText(this, "Documento não encontrado", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Erro ao buscar formulário", e)
                Toast.makeText(this, "Erro ao carregar formulário", Toast.LENGTH_SHORT).show()
            }
    }
}
