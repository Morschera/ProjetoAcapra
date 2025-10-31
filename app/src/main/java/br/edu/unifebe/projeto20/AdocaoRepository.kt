package br.edu.unifebe.projeto20.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import br.edu.unifebe.projeto20.Model.FormularioAdocao
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class AdocaoRepository {

    private val db = Firebase.firestore

    fun salvarFormulario(context: Context, formulario: FormularioAdocao, onComplete: () -> Unit) {
        db.collection("formulários")
            .add(formulario)
            .addOnSuccessListener { documentReference ->
                Log.d("Firestore", "Formulário adicionado com ID: ${documentReference.id}")
                onComplete()
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Erro ao adicionar documento", e)
                Toast.makeText(context, "Erro ao enviar formulário.", Toast.LENGTH_SHORT).show()
                onComplete()
            }
    }
}
