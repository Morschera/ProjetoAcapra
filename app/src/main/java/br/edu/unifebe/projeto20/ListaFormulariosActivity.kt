package br.edu.unifebe.projeto20

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class ListaFormulariosActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var listView: ListView
    private val formularios = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>

    companion object {
        private const val TAG = "ListaFormulariosActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_formularios)

        listView = findViewById(R.id.listView)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, formularios)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(this, "Clicou em ${formularios[position]}", Toast.LENGTH_SHORT).show()
        }

        carregarFormularios()
    }

    private fun carregarFormularios() {
        db.collection("formularios")
            .get()
            .addOnSuccessListener { result ->
                formularios.clear()
                for (document in result) {
                    val nome = document.getString("nome")
                    if (nome != null) formularios.add(nome)
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Erro ao buscar documentos", exception)
                Toast.makeText(this, "Erro ao carregar formulários", Toast.LENGTH_SHORT).show()
            }
    }
}
