package br.edu.unifebe.projeto20

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.edu.unifebe.projeto20.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var clicked = false

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btTodos.setOnClickListener {
            clicked = true
            if (clicked) {
                binding.btTodos.setBackgroundResource(R.drawable.bg_button_enabled)
                binding.btTodos.setTextColor(Color.WHITE)
                binding.btCachorro.setBackgroundResource(R.drawable.bg_button_disabled)
                binding.btCachorro.setTextColor(R.color.dark_gray)
                binding.btGato.setBackgroundResource(R.drawable.bg_button_disabled)
                binding.btGato.setTextColor(R.color.dark_gray)
                binding.btLula.setBackgroundResource(R.drawable.bg_button_disabled)
                binding.btLula.setTextColor(R.color.dark_gray)
                binding.btBolsonaro.setBackgroundResource(R.drawable.bg_button_disabled)
                binding.btBolsonaro.setTextColor(R.color.dark_gray)
                binding.recyclerViewProducts.visibility = View.INVISIBLE
                binding.txtListTitle.text = "Todos"
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
                    binding.btLula.setBackgroundResource(R.drawable.bg_button_disabled)
                    binding.btLula.setTextColor(R.color.dark_gray)
                    binding.btBolsonaro.setBackgroundResource(R.drawable.bg_button_disabled)
                    binding.btBolsonaro.setTextColor(R.color.dark_gray)
                    binding.recyclerViewProducts.visibility = View.INVISIBLE
                    binding.txtListTitle.text = "Cachorro"
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
                        binding.btLula.setBackgroundResource(R.drawable.bg_button_disabled)
                        binding.btLula.setTextColor(R.color.dark_gray)
                        binding.btBolsonaro.setBackgroundResource(R.drawable.bg_button_disabled)
                        binding.btBolsonaro.setTextColor(R.color.dark_gray)
                        binding.recyclerViewProducts.visibility = View.INVISIBLE
                        binding.txtListTitle.text = "Gato"
                    }

                    binding.btLula.setOnClickListener {
                        clicked = true
                        if (clicked) {
                            binding.btLula.setBackgroundResource(R.drawable.bg_button_enabled)
                            binding.btLula.setTextColor(Color.WHITE)
                            binding.btTodos.setBackgroundResource(R.drawable.bg_button_disabled)
                            binding.btTodos.setTextColor(R.color.dark_gray)
                            binding.btCachorro.setBackgroundResource(R.drawable.bg_button_disabled)
                            binding.btCachorro.setTextColor(R.color.dark_gray)
                            binding.btGato.setBackgroundResource(R.drawable.bg_button_disabled)
                            binding.btGato.setTextColor(R.color.dark_gray)
                            binding.btBolsonaro.setBackgroundResource(R.drawable.bg_button_disabled)
                            binding.btBolsonaro.setTextColor(R.color.dark_gray)
                            binding.recyclerViewProducts.visibility = View.INVISIBLE
                            binding.txtListTitle.text = "Lula"
                        }

                        binding.btBolsonaro.setOnClickListener {
                            clicked = true
                            if (clicked) {
                                binding.btBolsonaro.setBackgroundResource(R.drawable.bg_button_enabled)
                                binding.btBolsonaro.setTextColor(Color.WHITE)
                                binding.btTodos.setBackgroundResource(R.drawable.bg_button_disabled)
                                binding.btTodos.setTextColor(R.color.dark_gray)
                                binding.btCachorro.setBackgroundResource(R.drawable.bg_button_disabled)
                                binding.btCachorro.setTextColor(R.color.dark_gray)
                                binding.btGato.setBackgroundResource(R.drawable.bg_button_disabled)
                                binding.btGato.setTextColor(R.color.dark_gray)
                                binding.btLula.setBackgroundResource(R.drawable.bg_button_disabled)
                                binding.btLula.setTextColor(R.color.dark_gray)
                                binding.recyclerViewProducts.visibility = View.INVISIBLE
                                binding.txtListTitle.text = "Bolsonaro"
                            }
                        }
                    }
                }
            }
        }
    }
}