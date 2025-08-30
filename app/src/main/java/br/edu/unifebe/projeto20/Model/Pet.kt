package br.edu.unifebe.projeto20.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pet(
    val nome: String = "",
    val descricao: String = "",
    val imagemUrl: String = "",
    val idade: String = "",
    val sexo: String = "",
    val tipo: String = "",
    val porte: String = "",
    val vacinado: Boolean = false,
    val castrado: Boolean = false
) : Parcelable
