package br.edu.unifebe.projeto20

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.unifebe.projeto20.Model.Formulario
import br.edu.unifebe.projeto20.adapter.FormularioAdapter
import com.google.firebase.firestore.FirebaseFirestore
import androidx.recyclerview.widget.RecyclerView

class AdmFormularioActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.adm_formularios)

        val btnPet = findViewById<Button>(R.id.btnPet)
        val btnRegistrarAdmin = findViewById<Button>(R.id.btnRegistraAdm)
        val btnFormularios = findViewById<Button>(R.id.btnForms)

        val intentMain = Intent(this, MainActivity::class.java)

        btnPet.setOnClickListener {
            startActivity(intentMain)
        }

        btnRegistrarAdmin.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        btnFormularios.setOnClickListener {
            Toast.makeText(this, "Você já está nesta tela.", Toast.LENGTH_SHORT).show()
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewFormularios)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val db = FirebaseFirestore.getInstance()
        db.collection("formulários").get()
            .addOnSuccessListener { result ->
                val listaFormularios = result.map { doc ->
                    Formulario(doc.id, doc.getString("nomeCompleto") ?: "Sem nome")
                }
                recyclerView.adapter = FormularioAdapter(listaFormularios) { formulario ->
                    val intent = Intent(this, DetalheFormularioActivity::class.java)
                    intent.putExtra(DetalheFormularioActivity.EXTRA_FORM_ID, formulario.id)
                    startActivity(intent)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao carregar formulários", Toast.LENGTH_SHORT).show()
            }




    }
}

