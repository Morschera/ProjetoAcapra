package br.edu.unifebe.projeto20.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.unifebe.projeto20.MainActivity
import br.edu.unifebe.projeto20.Model.FormularioAdocao
import br.edu.unifebe.projeto20.R
import br.edu.unifebe.projeto20.repository.AdocaoRepository
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class FormularioAdocaoActivity : AppCompatActivity() {

    private lateinit var adocaoRepository: AdocaoRepository
    private val storage = FirebaseStorage.getInstance()
    private val listaImagensSelecionadas = mutableListOf<Uri>()
    private val imagensUrls = mutableListOf<String>()
    private lateinit var progressBar: ProgressBar

    private val selecionarImagensLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val clipData = result.data!!.clipData
                val imageUri = result.data!!.data

                if (clipData != null) {
                    for (i in 0 until clipData.itemCount) {
                        listaImagensSelecionadas.add(clipData.getItemAt(i).uri)
                    }
                } else if (imageUri != null) {
                    listaImagensSelecionadas.add(imageUri)
                }

                Toast.makeText(
                    this,
                    "${listaImagensSelecionadas.size} imagem(ns) selecionada(s)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.formulario_view)

        progressBar = findViewById(R.id.progressBar)
        adocaoRepository = AdocaoRepository()





        val nomeEditText: EditText = findViewById(R.id.edtNomeCompleto)
        val telefoneEditText: EditText = findViewById(R.id.edtTelefone)
        val animalEditText: EditText = findViewById(R.id.edtAnimalInteresse)
        val animalInteresse = intent.getStringExtra("animal_interesse")
        if (!animalInteresse.isNullOrEmpty()) {
            animalEditText.setText(animalInteresse)
        }
        val idadeEditText: EditText = findViewById(R.id.edtIdade)
        val rgConcordaResidencia: RadioGroup = findViewById(R.id.rgConcordaResidencia)
        val outrosAnimaisEditText: EditText = findViewById(R.id.edtOutrosAnimais)
        val rgCastradosVacinados: RadioGroup = findViewById(R.id.rgCastradosVacinados)
        val casaPropriaEditText: EditText = findViewById(R.id.edtCasaPropria)
        val rgLocalSeguro: RadioGroup = findViewById(R.id.rgLocalSeguro)
        val cepEditText: EditText = findViewById(R.id.edtCep)
        val cidadeEditText: EditText = findViewById(R.id.edtCidade)
        val ufEditText: EditText = findViewById(R.id.edtUf)
        val ruaNumeroEditText: EditText = findViewById(R.id.edtRuaNumero)
        val bairroEditText: EditText = findViewById(R.id.edtBairro)
        val rendaEditText: EditText = findViewById(R.id.edtRenda)
        val condicoesEditText: EditText = findViewById(R.id.edtCondicoes)
        val rgVacinacao: RadioGroup = findViewById(R.id.rgVacinacao)
        val rgTaxaDoacao: RadioGroup = findViewById(R.id.rgTaxaDoacao)

        val enviarButton: Button = findViewById(R.id.btnEnviarFormulario)
        val btnVoltar = findViewById<ImageButton>(R.id.btnVoltar)
        val btnSelecionarImagem = findViewById<Button>(R.id.btnSelecionarImagem)

        val logo = findViewById<ImageView>(R.id.Logo)

        logo.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnSelecionarImagem.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }
            selecionarImagensLauncher.launch(intent)
        }

        enviarButton.setOnClickListener {
            if (!validarCamposObrigatorios(
                    nomeEditText, telefoneEditText, animalEditText, idadeEditText,
                    rgConcordaResidencia, outrosAnimaisEditText, rgCastradosVacinados,
                    casaPropriaEditText, rgLocalSeguro, cepEditText, cidadeEditText,
                    ufEditText, ruaNumeroEditText, bairroEditText, rendaEditText,
                    condicoesEditText, rgVacinacao, rgTaxaDoacao
                )
            ) return@setOnClickListener

            progressBar.visibility = View.VISIBLE

            if (listaImagensSelecionadas.isNotEmpty()) {
                uploadImagens { urls ->
                    salvarFormulario(
                        nomeEditText.text.toString(),
                        telefoneEditText.text.toString(),
                        animalEditText.text.toString(),
                        idadeEditText.text.toString().toInt(),
                        getRadioGroupValue(rgConcordaResidencia) == "Sim",
                        outrosAnimaisEditText.text.toString(),
                        getRadioGroupValue(rgCastradosVacinados) == "Sim",
                        casaPropriaEditText.text.toString(),
                        getRadioGroupValue(rgLocalSeguro) == "Sim",
                        cepEditText.text.toString(),
                        cidadeEditText.text.toString(),
                        ufEditText.text.toString(),
                        ruaNumeroEditText.text.toString(),
                        bairroEditText.text.toString(),
                        rendaEditText.text.toString().toInt(),
                        condicoesEditText.text.toString(),
                        getRadioGroupValue(rgVacinacao) == "Sim",
                        getRadioGroupValue(rgTaxaDoacao) == "Sim",
                        urls
                    )
                }
            } else {
                Toast.makeText(this, "Selecione ao menos uma imagem!", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
            }
        }

        btnVoltar.setOnClickListener { finish() }
    }

    private fun validarCamposObrigatorios(
        vararg campos: Any
    ): Boolean {
        for (campo in campos) {
            when (campo) {
                is EditText -> {
                    if (campo.text.toString().trim().isEmpty()) {
                        campo.error = "Campo obrigatório"
                        campo.requestFocus()
                        return false
                    }
                }
                is RadioGroup -> {
                    if (campo.checkedRadioButtonId == -1) {
                        Toast.makeText(this, "Selecione uma opção obrigatória", Toast.LENGTH_SHORT).show()
                        return false
                    }
                }
            }
        }
        return true
    }

    private fun salvarFormulario(
        nome: String,
        telefone: String,
        animalInteresse: String,
        idade: Int,
        concordaResidencia: Boolean,
        outrosAnimais: String,
        castradosVacinados: Boolean,
        tipoMoradia: String,
        localSeguro: Boolean,
        cep: String,
        cidade: String,
        uf: String,
        ruaNumero: String,
        bairro: String,
        renda: Int,
        condicoes: String,
        concordaVacinacao: Boolean,
        concordaTaxa: Boolean,
        imagens: List<String>
    ) {
        val formulario = FormularioAdocao(
            nomeCompleto = nome,
            telefone = telefone,
            animalInteresse = animalInteresse,
            idade = idade,
            concordaResidencia = concordaResidencia,
            outrosAnimais = outrosAnimais,
            castradosVacinados = castradosVacinados,
            casapropria = tipoMoradia,
            localSeguro = localSeguro,
            cep = cep,
            cidade = cidade,
            uf = uf,
            ruaNumero = ruaNumero,
            bairro = bairro,
            rendaMensal = renda,
            condicoesFisicasFinanceiras = condicoes,
            concordaVacinacao = concordaVacinacao,
            taxaDoacao = concordaTaxa,
            imagens = imagens
        )
        adocaoRepository.salvarFormulario(this, formulario) {
            runOnUiThread {
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Formulário enviado com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun uploadImagens(onComplete: (List<String>) -> Unit) {
        imagensUrls.clear()
        val total = listaImagensSelecionadas.size
        var uploaded = 0

        for (uri in listaImagensSelecionadas) {
            val ref = storage.reference.child("formularios/${UUID.randomUUID()}.jpg")

            ref.putFile(uri)
                .addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener { downloadUri ->
                        imagensUrls.add(downloadUri.toString())
                        uploaded++
                        if (uploaded == total) {
                            onComplete(imagensUrls.toList())
                        }
                    }
                }
                .addOnFailureListener { e ->
                    uploaded++
                    Toast.makeText(
                        this,
                        "Falha ao enviar uma imagem: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    if (uploaded == total) {
                        onComplete(imagensUrls.toList())
                    }
                }
        }
    }

    private fun getRadioGroupValue(radioGroup: RadioGroup): String {
        val checkedId = radioGroup.checkedRadioButtonId
        return if (checkedId != -1) {
            val radioButton: RadioButton = findViewById(checkedId)
            radioButton.text.toString()
        } else {
            ""
        }
    }
}
