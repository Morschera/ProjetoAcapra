package br.edu.unifebe.projeto20

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.unifebe.projeto20.adapter.FormAdapter
import com.google.firebase.firestore.FirebaseFirestore

class ListaFormulariosActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FormAdapter
    private val formularios = mutableListOf<String>()

    companion object {
        private const val TAG = "ListaFormulariosActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_formularios)

        val btnVoltar = findViewById<ImageButton>(R.id.btnVoltar)
        btnVoltar.setOnClickListener {
            finish()

            recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = FormAdapter(formularios) { nome ->
            Toast.makeText(this, "Clicou em $nome", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter

        carregarFormularios()
    }

    @SuppressLint("LongLogTag")
    private fun carregarFormularios() {
        db.collection("formularios")
            .get()
            .addOnSuccessListener { result ->
                formularios.clear() // limpa lista antiga
                for (document in result) {
                    val nome = document.getString("nome")
                    if (nome != null) {
                        formularios.add(nome)
                    }
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
