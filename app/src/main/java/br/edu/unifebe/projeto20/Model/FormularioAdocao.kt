package br.edu.unifebe.projeto20.Model

import android.graphics.Bitmap

data class FormularioAdocao (
    val nomeCompleto: String,  //Nome completo da pessoa
    val telefone: String,   //Telefone da pessoa
    val animalInteresse: String,    //Animal que a pessoa esta interessada em levar
    val idade: Int, //Idade da pessoa
    val concordaResidencia: Boolean,    //Outras pessoas dentro da residência dela aceitariam o cachorro/gato?
    val outrosAnimais: String,  //A pessoa tem outros animais?
    val castradosVacinados: Boolean,    //Os outros animais são castrados?
    val casapropria: String,    //A pessoa tem casa/apartamento próprios?
    val localSeguro: Boolean,   //O apartamento ou casa é seguro para o gato ou cachorro?
    val cep: String,   //Inserir o CEP para puxar da API ViaCep
    val cidade: String, //Será o campo localidade da API ViaCep
    val uf: String, //Será o campo uf da API ViaCep
    val ruaNumero: String, //A pessoa botará a rua e o número da casa
    val bairro: String, //A pessoa botará o seu bairro
    val rendaMensal: Int,  //A pessoa irá inserir a renda como exemplo: 1600
    val condicoesFisicasFinanceiras: String,   //A pessoa tem condicoes fisicas, mentais e financeiras para manter o animal
    val concordaVacinacao: Boolean, //A pessoa concorda que a vacinacao é obrigatória
    val taxaDoacao: Boolean, //A pessoa dirá se aceitará ser cobrado a taxa de 30 reais
    val imagens: String   //A pessoa insirirá as imagens complementares pedidas
    )