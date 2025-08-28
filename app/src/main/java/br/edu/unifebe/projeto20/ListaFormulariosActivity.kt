package br.edu.unifebe.projeto20

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.unifebe.projeto20.adapter.FormAdapter
import com.google.firebase.firestore.FirebaseFirestore

private val firestore: Any
private val firestore: Any

class ListaFormulariosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FormAdapter
    private val formularios = mutableListOf<String>() // Lista que vai ser preenchida pelo Firestore
    private val db = FirebaseFirestore.getInstance()

    companion object {
        private const val TAG = "ListaFormulariosActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_formularios)

        recyclerView = findViewById(R.id.recyclerView) // ID do RecyclerView no XML
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inicializa o adapter com a lista vazia
        adapter = FormAdapter(formularios) { nome ->
            Toast.makeText(this, "Clicou em $nome", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter

        carregarFormularios()
    }

    private fun carregarFormularios() {
        db.collection("formularios") // Certifique-se que o nome da coleção está igual ao do Firestore
            .get()
            .addOnSuccessListener { result ->
                formularios.clear() // limpa lista antiga
                for (document in result) {
                    val nome = document.getString("nome") // Campo "nome" do formulário no Firestore
                    if (nome != null) {
                        formularios.add(nome)
                    }
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
                adapter.notifyDataSetChanged() // Atualiza a lista no RecyclerView
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Erro ao buscar documentos", exception)
                Toast.makeText(this, "Erro ao carregar formulários", Toast.LENGTH_SHORT).show()
            }
    }
}
