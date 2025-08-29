package br.edu.unifebe.projeto20.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.unifebe.projeto20.Model.FormularioAdocao
import br.edu.unifebe.projeto20.R
import br.edu.unifebe.projeto20.repository.AdocaoRepository

class FormularioAdocaoActivity : AppCompatActivity() {

    // Declara a variável do repositório
    private lateinit var adocaoRepository: AdocaoRepository

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

        enviarButton.setOnClickListener {

            val nome = nomeEditText.text.toString()
            val telefone = telefoneEditText.text.toString()
            val animalInteresse = animalEditText.text.toString()
            val idade = idadeEditText.text.toString().toIntOrNull() ?: 0
            val concordaResidencia = getRadioGroupValue(rgConcordaResidencia) == "Sim"
            val outrosAnimais = outrosAnimaisEditText.text.toString()
            val castradosVacinados = getRadioGroupValue(rgCastradosVacinados) == "Sim"
            val tipoMoradia = casaPropriaEditText.text.toString()
            val localSeguro = getRadioGroupValue(rgLocalSeguro) == "Sim"
            val cep = cepEditText.text.toString()
            val cidade = cidadeEditText.text.toString()
            val uf = ufEditText.text.toString()
            val ruaNumero = ruaNumeroEditText.text.toString()
            val bairro = bairroEditText.text.toString()
            val renda = rendaEditText.text.toString().toIntOrNull() ?: 0
            val condicoes = condicoesEditText.text.toString()
            val concordaVacinacao = getRadioGroupValue(rgVacinacao) == "Sim"
            val concordaTaxa = getRadioGroupValue(rgTaxaDoacao) == "Sim"


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
                imagens = "" // ou a URL da imagem se você já a tiver
            )


            adocaoRepository.salvarFormulario(this, formulario)
        }

        btnVoltar.setOnClickListener {
            finish()
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
