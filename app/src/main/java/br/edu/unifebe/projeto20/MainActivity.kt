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

    private var colorDarkGray: Int = 0

    private enum class FilterType { TODOS, CACHORRO, GATO }

    private var currentFilterType: FilterType = FilterType.TODOS
    private var currentSearchQuery: String = ""

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
                    currentFilterType = FilterType.TODOS
                    applyFilters()
                    setSelectedButtons(binding.btTodos)
                }
            }
        }

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                currentSearchQuery = newText.orEmpty()
                applyFilters()
                return true
            }
        })

        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
            binding.searchView.requestFocus()
        }


        binding.btTodos.setOnClickListener {
            currentFilterType = FilterType.TODOS
            applyFilters()
            setSelectedButtons(binding.btTodos)
        }

        binding.btCachorro.setOnClickListener {
            currentFilterType = FilterType.CACHORRO
            applyFilters()
            setSelectedButtons(binding.btCachorro)
        }

        binding.btGato.setOnClickListener {
            currentFilterType = FilterType.GATO
            applyFilters()
            setSelectedButtons(binding.btGato)
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


    private fun applyFilters() {
        val filteredByType: List<Pet> = when (currentFilterType) {
            FilterType.TODOS -> fullList
            FilterType.CACHORRO -> fullList.filter {
                it.tipo.equals("Cachorro", ignoreCase = true)
            }
            FilterType.GATO -> fullList.filter {
                it.tipo.equals("Gato", ignoreCase = true)
            }
        }

        val finalFilteredList: List<Pet>
        if (currentSearchQuery.isEmpty()) {
            finalFilteredList = filteredByType
        } else {
            finalFilteredList = filteredByType.filter {
                it.nome.contains(currentSearchQuery, ignoreCase = true)
            }
        }

        petsList.clear()
        petsList.addAll(finalFilteredList)
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