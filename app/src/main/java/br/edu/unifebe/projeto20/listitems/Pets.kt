package br.edu.unifebe.projeto20.listitems

import br.edu.unifebe.projeto20.Model.Pet
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class Pets {

    private val firestore = FirebaseFirestore.getInstance()

    fun getPets(): Flow<MutableList<Pet>> = callbackFlow {
        val subscription = firestore.collection("pets")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val petList = snapshot?.documents?.mapNotNull { doc ->
                    try {
                        Pet(
                            nome = doc.getString("nome") ?: "",
                            descricao = doc.getString("descricao") ?: "",
                            imagemUrl = doc.getString("imagemUrl") ?: "",
                            idade = doc.getString("idade") ?: "",
                            sexo = doc.getString("sexo") ?: "",
                            tipo = doc.getString("tipo") ?: "",
                            porte = doc.getString("porte") ?: "",
                            vacinado = doc.getBoolean("vacinado") ?: false,
                            castrado = doc.getBoolean("castrado") ?: false
                        )
                    } catch (e: Exception) {
                        null
                    }
                }?.toMutableList() ?: mutableListOf()

                trySend(petList).isSuccess
            }

        awaitClose { subscription.remove() }
    }
}
