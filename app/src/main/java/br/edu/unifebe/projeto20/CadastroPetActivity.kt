package br.edu.unifebe.projeto20

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import br.edu.unifebe.projeto20.Model.Pet
import java.util.UUID

class CadastroPetActivity : AppCompatActivity() {

    private lateinit var imgPreview: ImageView
    private lateinit var edtNome: EditText
    private lateinit var edtDescricao: EditText
    private lateinit var edtIdade: EditText
    private lateinit var edtSexo: EditText
    private lateinit var edtTipo: EditText
    private lateinit var edtPorte: EditText
    private lateinit var chkVacinado: CheckBox
    private lateinit var chkCastrado: CheckBox
    private lateinit var btnSalvar: Button
    private lateinit var btnEscolherImagem: Button
    private lateinit var btnDeletar: Button
    private lateinit var spinnerPets: Spinner
    private lateinit var btnVoltar: ImageButton

    private var imageUri: Uri? = null
    private var petSelecionadoId: String? = null
    private val PICK_IMAGE_REQUEST = 1001

    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_pet)

        imgPreview = findViewById(R.id.imgPreview)
        edtNome = findViewById(R.id.edtNome)
        edtDescricao = findViewById(R.id.edtDescricao)
        edtIdade = findViewById(R.id.edtIdade)
        edtSexo = findViewById(R.id.edtSexo)
        edtTipo = findViewById(R.id.edtTipo)
        edtPorte = findViewById(R.id.edtPorte)
        chkVacinado = findViewById(R.id.chkVacinado)
        chkCastrado = findViewById(R.id.chkCastrado)
        btnSalvar = findViewById(R.id.btnSalvar)
        btnEscolherImagem = findViewById(R.id.btnEscolherImagem)
        btnDeletar = findViewById(R.id.btnDeletar)
        spinnerPets = findViewById(R.id.spinnerPets)
        btnVoltar = findViewById(R.id.btnVoltar)

        val logo = findViewById<ImageView>(R.id.Logo)
        logo.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }

        btnVoltar.setOnClickListener { finish() }
        btnEscolherImagem.setOnClickListener { escolherImagem() }
        btnSalvar.setOnClickListener { salvarOuAtualizarPet() }
        btnDeletar.setOnClickListener { deletarPet() }

        carregarPets()
    }

    private fun mostrarLoading(): AlertDialog {
        val view = layoutInflater.inflate(R.layout.dialogo_carregando, null)
        val dialog = AlertDialog.Builder(this)
            .setView(view)
            .setCancelable(false)
            .create()
        dialog.show()
        return dialog
    }

    private fun carregarPets() {
        db.collection("pets").get()
            .addOnSuccessListener { result ->
                val listaPets = result.map { it.id to (it.getString("nome") ?: "Sem nome") }
                val nomes = mutableListOf("Novo Pet")
                nomes.addAll(listaPets.map { it.second })

                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, nomes)
                spinnerPets.adapter = adapter

                spinnerPets.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                        if (position == 0) {
                            limparCampos()
                            petSelecionadoId = null
                        } else {
                            petSelecionadoId = listaPets[position - 1].first
                            carregarDadosPet(petSelecionadoId!!)
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }
            }
    }

    private fun carregarDadosPet(id: String) {
        db.collection("pets").document(id).get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    edtNome.setText(doc.getString("nome"))
                    edtDescricao.setText(doc.getString("descricao"))
                    edtIdade.setText(doc.getString("idade"))
                    edtSexo.setText(doc.getString("sexo"))
                    edtTipo.setText(doc.getString("tipo"))
                    edtPorte.setText(doc.getString("porte"))
                    chkVacinado.isChecked = doc.getBoolean("vacinado") ?: false
                    chkCastrado.isChecked = doc.getBoolean("castrado") ?: false

                    val imgUrl = doc.getString("imagemUrl")
                    if (!imgUrl.isNullOrEmpty()) {
                        Glide.with(this).load(imgUrl).into(imgPreview)
                    } else {
                        imgPreview.setImageResource(0)
                    }
                }
            }
    }

    private fun salvarOuAtualizarPet() {
        val nome = edtNome.text.toString().trim()
        val descricao = edtDescricao.text.toString().trim()
        val idade = edtIdade.text.toString().trim()
        val sexo = edtSexo.text.toString().trim()
        val tipo = edtTipo.text.toString().trim()
        val porte = edtPorte.text.toString().trim()
        val vacinado = chkVacinado.isChecked
        val castrado = chkCastrado.isChecked

        if (nome.isEmpty() || descricao.isEmpty() || idade.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            return
        }

        val loading = mostrarLoading()

        if (imageUri != null) {
            val fileRef = storage.child("pets/${UUID.randomUUID()}.jpg")
            fileRef.putFile(imageUri!!)
                .addOnSuccessListener {
                    fileRef.downloadUrl.addOnSuccessListener { uri ->
                        salvarPetNoBanco(nome, descricao, idade, sexo, tipo, porte, vacinado, castrado, uri.toString(), loading)
                    }
                }
                .addOnFailureListener { e ->
                    loading.dismiss()
                    Toast.makeText(this, "Erro ao enviar imagem: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            salvarPetNoBanco(nome, descricao, idade, sexo, tipo, porte, vacinado, castrado, null, loading)
        }
    }

    private fun salvarPetNoBanco(
        nome: String, descricao: String, idade: String, sexo: String,
        tipo: String, porte: String, vacinado: Boolean, castrado: Boolean,
        imagemUrl: String?, loading: AlertDialog
    ) {
        val petData = hashMapOf(
            "nome" to nome,
            "descricao" to descricao,
            "idade" to idade,
            "sexo" to sexo,
            "tipo" to tipo,
            "porte" to porte,
            "vacinado" to vacinado,
            "castrado" to castrado
        )

        if (imagemUrl != null) petData["imagemUrl"] = imagemUrl

        if (petSelecionadoId == null) {
            db.collection("pets").add(petData)
                .addOnSuccessListener {
                    loading.dismiss()
                    Toast.makeText(this, "Pet cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                    carregarPets()
                    limparCampos()
                }
                .addOnFailureListener {
                    loading.dismiss()
                    Toast.makeText(this, "Erro ao salvar pet!", Toast.LENGTH_SHORT).show()
                }
        } else {
            db.collection("pets").document(petSelecionadoId!!).update(petData as Map<String, Any>)
                .addOnSuccessListener {
                    loading.dismiss()
                    Toast.makeText(this, "Pet atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                    carregarPets()
                }
                .addOnFailureListener {
                    loading.dismiss()
                    Toast.makeText(this, "Erro ao atualizar pet!", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun deletarPet() {
        if (petSelecionadoId == null) {
            Toast.makeText(this, "Selecione um pet para deletar!", Toast.LENGTH_SHORT).show()
            return
        }

        val loading = mostrarLoading()

        db.collection("pets").document(petSelecionadoId!!).delete()
            .addOnSuccessListener {
                loading.dismiss()
                Toast.makeText(this, "Pet deletado com sucesso!", Toast.LENGTH_SHORT).show()
                carregarPets()
                limparCampos()
                petSelecionadoId = null
            }
            .addOnFailureListener {
                loading.dismiss()
                Toast.makeText(this, "Erro ao deletar pet!", Toast.LENGTH_SHORT).show()
            }
    }

    private fun escolherImagem() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            Glide.with(this).load(imageUri).into(imgPreview)
        }
    }

    private fun limparCampos() {
        edtNome.setText("")
        edtDescricao.setText("")
        edtIdade.setText("")
        edtSexo.setText("")
        edtTipo.setText("")
        edtPorte.setText("")
        chkVacinado.isChecked = false
        chkCastrado.isChecked = false
        imgPreview.setImageResource(0)
        imageUri = null
    }
}
