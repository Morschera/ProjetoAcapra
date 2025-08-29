package br.edu.unifebe.projeto20.Model

data class FormularioAdocao (
    val nomeCompleto: String = "",
    val telefone: String = "",
    val animalInteresse: String = "",
    val idade: Int = 0,
    val concordaResidencia: Boolean = false,
    val outrosAnimais: String = "",
    val castradosVacinados: Boolean = false,
    val casapropria: String = "",
    val localSeguro: Boolean = false,
    val cep: String = "",
    val cidade: String = "",
    val uf: String = "",
    val ruaNumero: String = "",
    val bairro: String = "",
    val rendaMensal: Int = 0,
    val condicoesFisicasFinanceiras: String = "",
    val concordaVacinacao: Boolean = false,
    val taxaDoacao: Boolean = false,
    val imagens: String = ""
)