package br.edu.unifebe.projeto20

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import br.edu.unifebe.projeto20.Model.Pet
import br.edu.unifebe.projeto20.adapter.PetsAdapter
import br.edu.unifebe.projeto20.databinding.ActivityMainBinding
import br.edu.unifebe.projeto20.listitems.Pets
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import androidx.core.graphics.toColorInt
import br.edu.unifebe.projeto20.view.FormularioAdocaoActivity

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var petsAdapter: PetsAdapter
    private val pets = Pets()
    private val petsList: MutableList<Pet> = mutableListOf()
    var clicked = false

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = "#E0E0E0".toColorInt()

        CoroutineScope(Dispatchers.IO).launch {
            pets.getPets().collectIndexed { index, value ->
                for (p in value) {
                    petsList.add(p)
                    Log.d("DEBUG", "Pets carregados: ${value.size}")
                }
                launch(Dispatchers.Main) {
                    petsAdapter.notifyDataSetChanged()
                }
            }
        }

        val recyclerViewPets = binding.recyclerViewProducts
        recyclerViewPets.layoutManager = GridLayoutManager(this, 2)
        recyclerViewPets.setHasFixedSize(true)
        petsAdapter = PetsAdapter(this,petsList)
        recyclerViewPets.adapter = petsAdapter

        binding.btTodos.setOnClickListener {
            clicked = true
            if (clicked) {
                binding.btTodos.setBackgroundResource(R.drawable.bg_button_enabled)
                binding.btTodos.setTextColor(Color.WHITE)
                binding.btCachorro.setBackgroundResource(R.drawable.bg_button_disabled)
                binding.btCachorro.setTextColor(R.color.dark_gray)
                binding.btGato.setBackgroundResource(R.drawable.bg_button_disabled)
                binding.btGato.setTextColor(R.color.dark_gray)
                binding.recyclerViewProducts.visibility = View.VISIBLE
                binding.txtListTitle.text = "Todos"
            }
        }
            binding.btCachorro.setOnClickListener {
                clicked = true
                if (clicked) {
                    binding.btCachorro.setBackgroundResource(R.drawable.bg_button_enabled)
                    binding.btCachorro.setTextColor(Color.WHITE)
                    binding.btTodos.setBackgroundResource(R.drawable.bg_button_disabled)
                    binding.btTodos.setTextColor(R.color.dark_gray)
                    binding.btGato.setBackgroundResource(R.drawable.bg_button_disabled)
                    binding.btGato.setTextColor(R.color.dark_gray)
                    binding.recyclerViewProducts.visibility = View.VISIBLE
                    binding.txtListTitle.text = "Cachorro"
                }
            }
                binding.btGato.setOnClickListener {
                    clicked = true
                    if (clicked) {
                        binding.btGato.setBackgroundResource(R.drawable.bg_button_enabled)
                        binding.btGato.setTextColor(Color.WHITE)
                        binding.btTodos.setBackgroundResource(R.drawable.bg_button_disabled)
                        binding.btTodos.setTextColor(R.color.dark_gray)
                        binding.btCachorro.setBackgroundResource(R.drawable.bg_button_disabled)
                        binding.btCachorro.setTextColor(R.color.dark_gray)
                        binding.recyclerViewProducts.visibility = View.VISIBLE
                        binding.txtListTitle.text = "Gato"
                    }
                        }

        val btnIrLogin = findViewById<Button>(R.id.btnIrLogin)
        val btnAdocao = findViewById<Button>(R.id.btnAdocao)

        btnIrLogin.setOnClickListener {
            val intentLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentLogin)
        }

        btnAdocao.setOnClickListener {
            val intentAdocao = Intent(this, FormularioAdocaoActivity::class.java)
            startActivity(intentAdocao)
        }

    }
}