package br.edu.unifebe.projeto20

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.GridLayoutManager
import br.edu.unifebe.projeto20.Model.Pet
import br.edu.unifebe.projeto20.adapter.PetsAdapter
import br.edu.unifebe.projeto20.databinding.ActivityMainBinding
import br.edu.unifebe.projeto20.listitems.Pets
import br.edu.unifebe.projeto20.view.FormularioAdocaoActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var petsAdapter: PetsAdapter

    private val pets = Pets()

    private val petsList: MutableList<Pet> = mutableListOf()

    private val fullList: MutableList<Pet> = mutableListOf()

    private var clicked = false
    private var colorDarkGray: Int = 0

    private enum class FilterType { TODOS, CACHORRO, GATO }

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = "#E0E0E0".toColorInt()
        colorDarkGray = ContextCompat.getColor(this, R.color.dark_gray)

        val recyclerViewPets = binding.recyclerViewProducts
        recyclerViewPets.layoutManager = GridLayoutManager(this, 2)
        recyclerViewPets.setHasFixedSize(true)
        petsAdapter = PetsAdapter(petsList) { pet ->
            val intent = Intent(this, DetalhesPetActivity::class.java)
            intent.putExtra("pet", pet)
            startActivity(intent)
        }
        binding.recyclerViewProducts.adapter = petsAdapter

        CoroutineScope(Dispatchers.IO).launch {
            pets.getPets().collectIndexed { _, value ->
                Log.d("DEBUG", "Pets carregados: ${value.size}")

                fullList.clear()
                fullList.addAll(value)

                petsList.clear()
                petsList.addAll(value)

                launch(Dispatchers.Main) {
                    petsAdapter.notifyDataSetChanged()
                    binding.txtListTitle.text = "Todos"
                    setSelectedButtons(binding.btTodos)
                }
            }
        }

        binding.btTodos.setOnClickListener {
            clicked = true
            if (clicked) {
                applyFilter(FilterType.TODOS)
                setSelectedButtons(binding.btTodos)
                binding.txtListTitle.text = "Todos"
            }
        }

        binding.btCachorro.setOnClickListener {
            clicked = true
            if (clicked) {
                applyFilter(FilterType.CACHORRO)
                setSelectedButtons(binding.btCachorro)
                binding.txtListTitle.text = "Cachorro"
            }
        }

        binding.btGato.setOnClickListener {
            clicked = true
            if (clicked) {
                applyFilter(FilterType.GATO)
                setSelectedButtons(binding.btGato)
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

    private fun applyFilter(type: FilterType) {
        val filtered: List<Pet> = when (type) {
            FilterType.TODOS -> fullList


            FilterType.CACHORRO -> fullList.filter {
                it.nome.contains("Cachorro", ignoreCase = true) ||
                        it.descricao.contains("Cachorro", ignoreCase = true)
            }
            FilterType.GATO -> fullList.filter {
                it.nome.contains("Gato", ignoreCase = true) ||
                        it.descricao.contains("Gato", ignoreCase = true)
            }
        }

        petsList.clear()
        petsList.addAll(filtered)
        petsAdapter.notifyDataSetChanged()
        binding.recyclerViewProducts.visibility = View.VISIBLE
    }

    private fun setSelectedButtons(selected: Button) {
        val all = listOf(binding.btTodos, binding.btCachorro, binding.btGato)
        all.forEach { btn ->
            val enabled = (btn == selected)
            if (enabled) {
                btn.setBackgroundResource(R.drawable.bg_button_enabled)
                btn.setTextColor(Color.WHITE)
            } else {
                btn.setBackgroundResource(R.drawable.bg_button_disabled)
                btn.setTextColor(colorDarkGray)
            }
        }
    }
}
