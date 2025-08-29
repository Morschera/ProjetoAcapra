package br.edu.unifebe.projeto20.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
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

        adocaoRepository = AdocaoRepository()

        val nomeEditText: EditText = findViewById(R.id.edtNomeCompleto)
        val telefoneEditText: EditText = findViewById(R.id.edtTelefone)
        val animalEditText: EditText = findViewById(R.id.edtAnimalInteresse)
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
        val btnVoltar = findViewById<Button>(R.id.btnVoltar)
        val btnSelecionarImagem = findViewById<Button>(R.id.btnSelecionarImagem)

        // Botão para selecionar imagens
        btnSelecionarImagem.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }
            selecionarImagensLauncher.launch(intent)
        }

        enviarButton.setOnClickListener {
            if (listaImagensSelecionadas.isNotEmpty()) {
                uploadImagens { urls ->
                    salvarFormulario(
                        nomeEditText.text.toString(),
                        telefoneEditText.text.toString(),
                        animalEditText.text.toString(),
                        idadeEditText.text.toString().toIntOrNull() ?: 0,
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
                        rendaEditText.text.toString().toIntOrNull() ?: 0,
                        condicoesEditText.text.toString(),
                        getRadioGroupValue(rgVacinacao) == "Sim",
                        getRadioGroupValue(rgTaxaDoacao) == "Sim",
                        urls
                    )
                }
            } else {
                salvarFormulario(
                    nomeEditText.text.toString(),
                    telefoneEditText.text.toString(),
                    animalEditText.text.toString(),
                    idadeEditText.text.toString().toIntOrNull() ?: 0,
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
                    rendaEditText.text.toString().toIntOrNull() ?: 0,
                    condicoesEditText.text.toString(),
                    getRadioGroupValue(rgVacinacao) == "Sim",
                    getRadioGroupValue(rgTaxaDoacao) == "Sim",
                    emptyList()
                )
            }
        }

        btnVoltar.setOnClickListener { finish() }
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
        adocaoRepository.salvarFormulario(this, formulario)
    }

    private fun uploadImagens(onComplete: (List<String>) -> Unit) {
        imagensUrls.clear()
        val total = listaImagensSelecionadas.size
        var uploaded = 0

        for (uri in listaImagensSelecionadas) {
            val ref = storage.reference.child("formularios/${UUID.randomUUID()}.jpg")
            ref.putFile(uri).addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener { downloadUri ->
                    imagensUrls.add(downloadUri.toString())
                    uploaded++
                    if (uploaded == total) {
                        onComplete(imagensUrls.toList()) // retorna lista
                    }
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
