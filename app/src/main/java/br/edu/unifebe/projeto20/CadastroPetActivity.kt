package br.edu.unifebe.projeto20

import android.app.Activity
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

    private var imageUri: Uri? = null

    private val PICK_IMAGE_REQUEST = 1001

    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance().reference
    private lateinit var btnVoltar: ImageButton

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
        btnVoltar = findViewById(R.id.btnVoltar)

        btnVoltar.setOnClickListener {
            finish() // Fecha a Activity e retorna pra anterior
        }

        btnEscolherImagem.setOnClickListener {
            escolherImagem()
        }

        btnSalvar.setOnClickListener {
            salvarPet()
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

    private fun salvarPet() {
        val nome = edtNome.text.toString().trim()
        val descricao = edtDescricao.text.toString().trim()
        val idade = edtIdade.text.toString().trim()  // agora é string
        val sexo = edtSexo.text.toString().trim()
        val tipo = edtTipo.text.toString().trim()
        val porte = edtPorte.text.toString().trim()
        val vacinado = chkVacinado.isChecked
        val castrado = chkCastrado.isChecked

        if (nome.isEmpty() || descricao.isEmpty() || idade.isEmpty() || imageUri == null) {
            Toast.makeText(this, "Preencha todos os campos e selecione uma imagem!", Toast.LENGTH_SHORT).show()
            return
        }

        // Upload da imagem para o Firebase Storage
        val fileRef = storage.child("pets/${UUID.randomUUID()}.jpg")
        fileRef.putFile(imageUri!!)
            .addOnSuccessListener {
                fileRef.downloadUrl.addOnSuccessListener { uri ->

                    val pet = Pet(
                        nome = nome,
                        descricao = descricao,
                        idade = idade,
                        sexo = sexo,
                        tipo = tipo,
                        porte = porte,
                        vacinado = vacinado,
                        castrado = castrado,
                        imagemUrl = uri.toString()
                    )

                    db.collection("pets")
                        .add(pet)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Pet cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Erro ao salvar: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Falha no upload da imagem: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
